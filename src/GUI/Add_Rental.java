package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static Database.DbConnection.*;
import Model.Rental;

@SuppressWarnings("serial")
public class Add_Rental extends JPanel
{
	JButton addRentalBtn, refreshListBtn;
	JTextField returnDateFld, rentalDateFld;
	JComboBox<String> cutomerNameFld ,filmTitleFld ;
	JPanel centerPanel;
	JLabel rentedFromLbl,rentedToLbl;

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
		rentedFromLbl = new JLabel("Rented From: ");
		rentedToLbl = new JLabel("Rented Till : ");
		rentedFromLbl.setFont(new Font("Tahoma", 1, 18)); // NOI18N
		rentedToLbl.setFont(new Font("Tahoma", 1, 18)); // NOI18N
		Vector<String> filmList = null;
		Vector<String> customerList = null;
		try
		{
			filmList = getAllFilmTitles();
			customerList = getAllCustomer();
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		cutomerNameFld = new JComboBox<String>(customerList);
		filmTitleFld = new JComboBox<String>(filmList);
		cutomerNameFld.setSize(5,5);

		centerPanel.add(customerLbl);
		centerPanel.add(cutomerNameFld);
		centerPanel.add(filmLbl);
		centerPanel.add(filmTitleFld);
		centerPanel.add(rentedFromLbl);
		centerPanel.add(rentedToLbl);
		

		/***********************************************************************************/
		JPanel btnPanel = new JPanel();

		btnPanel.setLayout(new FlowLayout());

		superCenterPanel.add(btnPanel);
		addRentalBtn = new JButton("Add Rental");
		addRentalBtn.setSize(5,5);
		addRentalBtn.addActionListener(new AddRentalListner());
		btnPanel.add(addRentalBtn);
		refreshListBtn = new JButton("Refresh Page");
		refreshListBtn.setSize(5,5);
		refreshListBtn.addActionListener(new RefreshActionListner());
		btnPanel.add(refreshListBtn);
		
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
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
				  LocalDateTime now = LocalDateTime.now();
				  String rentedFrom = dtf.format(now);
				  String rentedTill = dtf.format(now.plusDays(rentalDuration));
				  rentedFromLbl.setText("Rented On: "+rentedFrom);
				  rentedToLbl.setText("Rented Till: "+rentedTill);
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
	
	
//create inner class listener object
	private class RefreshActionListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{ 
				try
				{
					Vector<String> filmList = null;
					Vector<String> customerList = null;
					filmList = getAllFilmTitles();
					customerList = getAllCustomer();
					for(String film : filmList)
						filmTitleFld.addItem(film);
					for(String customer : customerList)
						cutomerNameFld.addItem(customer);
					
				  rentedFromLbl.setText("Rented On: ");
				  rentedToLbl.setText("Rented Till: ");
					JOptionPane.showMessageDialog(null, "Page Refreshed", "Success", JOptionPane.INFORMATION_MESSAGE);
				}  catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
		}//end actionPerformed()

	}//end inner class


}
