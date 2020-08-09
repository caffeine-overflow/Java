/**
 * Program Name	: Add_Rental.java
 * Purpose			: A panel to add a new Rental Transaction
 * Author				: Prabin Gyawali (0877282) and Danish Davish (0691688)
 * Date					: Aug. 2, 2020
 */
package GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static Database.DbConnection.*;
import Model.Rental;

@SuppressWarnings("serial")
public class Add_Rental extends JPanel
{
	//Declare required components
	JButton addRentalBtn, refreshListBtn;
	JTextField returnDateFld, rentalDateFld;
	JComboBox<String> cutomerNameFld ,filmTitleFld ;
	JPanel centerPanel;
	JLabel rentedFromLbl,rentedToLbl;
	//Constructor
	public Add_Rental() {
		//Boiler Plate
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

		//Initialize 
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
		//Get all the films and customers from database
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


	//create inner class action listener object for Add rental button
	private class AddRentalListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{ 
			try
			{
				Rental rental = new Rental();
				//get the customer and film, id and name separated
				String[] customer = cutomerNameFld.getSelectedItem().toString().split("@id::");
				String[] film = filmTitleFld.getSelectedItem().toString().split("@id::");
				//get the customer and film id
				int customerID = Integer.parseInt(customer[1]);
				int filmID = Integer.parseInt(film[1]);

				//insert the transaction and insert it into inventory and get the id
				int inventoryID=insertInventory(filmID);
				//get the rental duration
				int rentalDuration = getRentalDurationForFilm(filmID);
				//Date Time format
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy / MM /dd");  
				//get current date and use it as rented on date
				LocalDateTime now = LocalDateTime.now();
				String rentedFrom = dtf.format(now);
				//Add the rental duration and use it as rented till
				//NOTE: The rented on and rented till is also calculated on backend
				String rentedTill = dtf.format(now.plusDays(rentalDuration));
				rentedFromLbl.setText("Rented On: "+rentedFrom);
				rentedToLbl.setText("Rented Till: "+rentedTill);
				rental.setCustomerID(customerID);
				rental.setFilmID(filmID);
				rental.setInventoryID(inventoryID);
				rental.setRentalDuration(rentalDuration);
				insertRental(rental); //Insert new rental
				JOptionPane.showMessageDialog(null, "Rented "+film[0].trim()+" to "+customer[0], "Success", JOptionPane.INFORMATION_MESSAGE);
			}  catch (Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}//end actionPerformed()

	}//end inner class


	//create inner class Action listener object for refresh button
	//We need to refresh the page because, new film or customer added aren't shown
	private class RefreshActionListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{ 
			try
			{
				//Get the all movies and customer from database and set it to the combo box
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
