/**
 * Program Name	: Add_Customer.java
 * Purpose			: A panel to add a new customer
 * Author				: Prabin Gyawali (0877282) and Danish Davish (0691688)
 * Date					: Aug. 2, 2020
 */
package GUI;

import static Validator.SakilaValidator.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static Database.DbConnection.*;
import Model.Address;
import Model.Customer;

@SuppressWarnings("serial")
public class Add_Customer extends JPanel
{
	//Create components
	private JButton addCustomerBtn, clearCustomerAdd;
	private JTextField firstNameFld,emailFld,  lastNameFld, addressFld,postalFld, phoneFld;
	private JComboBox<String> cityFld,districtFld,countryFld ;
	private Vector<String> countryArray, cityArray, districtArray;
	JPanel centerPanel;
	//constructor
	public Add_Customer()
	{
		//Boiler plate
		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());

		/***********************************************************************************/
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(9,2,10,10) );
		this.add(centerPanel, BorderLayout.CENTER);

		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lastNameLbl = new JLabel("Last Name:");
		lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel phoneLbl = new JLabel("Phone:");		
		phoneLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel emailLbl = new JLabel("Email:");		
		emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel addressLbl = new JLabel("Address:");		
		addressLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel cityLbl = new JLabel("City:");		
		cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel districtLbl = new JLabel("Distict:");		
		districtLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel postalLbl = new JLabel("Postal code:");		
		postalLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel countryLbl = new JLabel("Country:");		
		countryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		try
		{
			countryArray = getAllCountry(); //get all the countries from database
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		//Initialize all the components
		districtArray=new Vector<String>();
		cityArray=new Vector<String>();
		firstNameFld = new JTextField();
		lastNameFld = new JTextField();
		phoneFld  = new JTextField();
		emailFld = new JTextField();
		cityFld = new JComboBox<String>(cityArray);
		postalFld = new JTextField();
		districtFld = new JComboBox<String>(districtArray);
		addressFld = new JTextField();
		countryFld = new JComboBox<String>(countryArray);
		countryFld.addItemListener(new CountryDetails());
		districtFld.addItemListener(new CountryDetails());
		cityFld.addItemListener(new CountryDetails());

		//Add all the components to the panel
		centerPanel.add(firstNameLbl);
		centerPanel.add(firstNameFld);
		centerPanel.add(lastNameLbl);
		centerPanel.add(lastNameFld);
		centerPanel.add(phoneLbl);
		centerPanel.add(phoneFld);	
		centerPanel.add(emailLbl);
		centerPanel.add(emailFld);	
		centerPanel.add(countryLbl);
		centerPanel.add(countryFld);	
		centerPanel.add(districtLbl);
		centerPanel.add(districtFld);	
		centerPanel.add(cityLbl);
		centerPanel.add(cityFld);
		centerPanel.add(addressLbl);
		centerPanel.add(addressFld);				
		centerPanel.add(postalLbl);
		centerPanel.add(postalFld);	
		countryFld.setName("country");
		cityFld.setName("city");
		districtFld.setName("district");
		cityFld.setEnabled(false);
		districtFld.setEnabled(false);
		/***********************************************************************************/
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPanel.setLayout(new FlowLayout() );

		this.add(btnPanel, BorderLayout.SOUTH);

		addCustomerBtn = new JButton("Add");
		clearCustomerAdd = new JButton("Clear");
		addCustomerBtn.addActionListener(new AddCustomerPage());
		clearCustomerAdd.addActionListener(new AddCustomerPage());
		btnPanel.add(addCustomerBtn);
		btnPanel.add(clearCustomerAdd);

	}	


	//create inner class listener object for combobox
	private class CountryDetails implements ItemListener	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				@SuppressWarnings("unchecked")
				String calledBy =((JComboBox<String>)e.getSource()).getName();
				try
				{
					if(calledBy.equals("country")) {	//if the country field is changed get all the cities and district for that country			
						districtArray = getAllDistrictInCountry(countryFld.getSelectedItem().toString());
						districtFld.removeAllItems();
						for(String district:districtArray)
							districtFld.addItem(district);
						districtFld.setEnabled(true);
						resetComboBoxBorder(cityFld,districtFld,countryFld);

					}
					else if(calledBy.equals("district")) {				//if certain district is changed, get all the cities in that district
						cityArray = getAllCitiesInDistrict(districtFld.getSelectedItem().toString());
						cityFld.removeAllItems();
						for(String city:cityArray)
							cityFld.addItem(city);
						cityFld.setEnabled(true);

					}
				} catch (SQLException e1)
				{
					JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}//end inner class


	//create inner class action listener object
	private class AddCustomerPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add")) //add the customer
			{	
				//validate all text fields and comboBox
				boolean validTextFields = areTextFieldValid(firstNameFld,emailFld,  lastNameFld, addressFld,postalFld,phoneFld);
				boolean validComboBoxes = areComboBoxValid(cityFld,districtFld,countryFld);

				if( validTextFields && validComboBoxes	) {
					Customer customer = getCustomer(); //create the customer object from  the values in the text box and combobox
					try
					{
						//Validate and insert the customer
						validateCustomer(customer);
						insertCustomer(customer);
						JOptionPane.showMessageDialog(null, "Successfully added Mr. "+firstNameFld.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
						clearPanel(centerPanel);

					} catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter the value in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getActionCommand().equals("Clear"))
				clearPanel(centerPanel);
		}//end actionPerformed()

		/**
		 * Get all the data from the current panel 
		 * creates a customer and address object and returns it
		 * @return a customer object
		 */
		private Customer getCustomer()
		{
			Customer customer = new Customer();
			Address address = new Address();
			address.setCity(cityFld.getSelectedItem().toString());
			address.setCountry(countryFld.getSelectedItem().toString());
			address.setDistrict(districtFld.getSelectedItem().toString());
			address.setStreetAddress(addressFld.getText());
			address.setPhone(phoneFld.getText());
			address.setPostalCode(postalFld.getText());
			customer.setFirstName(firstNameFld.getText());
			customer.setLastName(lastNameFld.getText());
			customer.setEmail(emailFld.getText());
			customer.setAddress(address);
			return customer;
		}

	}//end inner class
}