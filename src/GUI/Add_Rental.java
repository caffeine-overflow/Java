package GUI;
import static Validator.SakilaValidator.areTextFieldValid;
import static Validator.SakilaValidator.clearPanel;
import static Validator.SakilaValidator.validateActor;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Database.DbConnection;
import Model.Actor;
import Model.Rental;

public class Add_Rental extends JPanel
{
	JButton addRentalBtn, clearRentalAdd;
	JTextField returnDateFld, rentalDateFld;
	JComboBox cutomerNameFld ,filmTitleFld ;
	JPanel centerPanel;

	public Add_Rental() {

		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(30, 30, 30, 30));
		this.setLayout(new BorderLayout() );

		/***********************************************************************************/
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,2,10,250) );
		this.add(centerPanel, BorderLayout.CENTER);

		JLabel customerNameLbl = new JLabel("Customer Name:");
		customerNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel filmTitleLbl = new JLabel("Fim Title:");
		filmTitleLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel rentalDateLbl = new JLabel("Rental Date:");		
		rentalDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel returnDateLbl = new JLabel("Return Date:");		
		returnDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		Vector<String> filmList = null;
		Vector<String> customerList = null;
		try
		{
			filmList = new DbConnection().getAllFilmTitles();
			customerList = new DbConnection().getAllCustomer();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cutomerNameFld = new JComboBox<String>(customerList);
		filmTitleFld = new JComboBox<String>(filmList);
		rentalDateFld  = new JTextField();
		returnDateFld = new JTextField();


		centerPanel.add(customerNameLbl);
		centerPanel.add(cutomerNameFld);
		centerPanel.add(filmTitleLbl);
		centerPanel.add(filmTitleFld);


		/***********************************************************************************/
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPanel.setLayout(new FlowLayout() );

		this.add(btnPanel, BorderLayout.SOUTH);

		addRentalBtn = new JButton("Add Rental");
		addRentalBtn.addActionListener(new AddRentalListner());
		btnPanel.add(addRentalBtn);
	}	


	//create inner class listener object
	private class AddRentalListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{ 
				try
				{
					Rental rental = new Rental();
					String[] customer = cutomerNameFld.getSelectedItem().toString().split("@id::");
					String[] film = filmTitleFld.getSelectedItem().toString().split("@id::");
					int customerID = Integer.parseInt(customer[1]);
					int filmID = Integer.parseInt(film[1]);

					int inventoryID=new DbConnection().insertInventory(filmID);
					int rentalDuration = new DbConnection().getRentalDurationForFilm(filmID);
					
					rental.setCustomerID(customerID);
					rental.setFilmID(filmID);
					rental.setInventoryID(inventoryID);
					rental.setRentalDuration(rentalDuration);
					new DbConnection().insertRental(rental);
					JOptionPane.showMessageDialog(null, "Rented "+film[0].trim()+" to "+customer[0], "Error", JOptionPane.ERROR_MESSAGE);
					clearPanel(centerPanel);
				}  catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			

		}//end actionPerformed()

	}//end inner class


}
