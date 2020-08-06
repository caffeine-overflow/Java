package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static Database.DbConnection.*;
import Model.Rental;

@SuppressWarnings("serial")
public class Add_Rental extends JPanel
{
	JButton addRentalBtn, clearRentalAdd;
	JTextField returnDateFld, rentalDateFld;
	JComboBox<String> cutomerNameFld ,filmTitleFld ;
	JPanel centerPanel;

	public Add_Rental() {
		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout() );
		/***********************************************************************************/
		JPanel superCenterPanel = new JPanel(new GridLayout(2, 1, 30, 30));
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3, 2, 10, 50));
		superCenterPanel.add(centerPanel);
		this.add(superCenterPanel,BorderLayout.CENTER);

		JLabel customerLbl = new JLabel("Customer Name:");
		customerLbl.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel filmLbl = new JLabel("Fim Title:");
		filmLbl.setHorizontalAlignment(SwingConstants.LEFT);

		Vector<String> filmList = null;
		Vector<String> customerList = null;
		try
		{
			filmList = getAllFilmTitles();
			customerList = getAllCustomer();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cutomerNameFld = new JComboBox<String>(customerList);
		filmTitleFld = new JComboBox<String>(filmList);
		cutomerNameFld.setSize(5,5);

		centerPanel.add(customerLbl);
		centerPanel.add(cutomerNameFld);
		centerPanel.add(filmLbl);
		centerPanel.add(filmTitleFld);
		

		/***********************************************************************************/
		JPanel btnPanel = new JPanel();

		btnPanel.setLayout(new FlowLayout());

		superCenterPanel.add(btnPanel);
		addRentalBtn = new JButton("Add Rental");
		addRentalBtn.setSize(5,5);
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

					int inventoryID=insertInventory(filmID);
					int rentalDuration = getRentalDurationForFilm(filmID);
					
					rental.setCustomerID(customerID);
					rental.setFilmID(filmID);
					rental.setInventoryID(inventoryID);
					rental.setRentalDuration(rentalDuration);
					insertRental(rental);
					JOptionPane.showMessageDialog(null, "Rented "+film[0].trim()+" to "+customer[0], "Success", JOptionPane.INFORMATION_MESSAGE);
				}  catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
		}//end actionPerformed()

	}//end inner class


}
