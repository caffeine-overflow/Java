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

import Database.DbConnection;
import Model.Actor;

public class Add_Customer extends JPanel
{
	JButton addCustomerBtn, clearCustomerAdd;
	JTextField firstNameFld,emailFld,  lastNameFld, addressFld,postalFld, phoneFld;
	JComboBox<String> cityFld,districtFld,countryFld ;
	Vector<String> countryArray, cityArray, districtArray;
	JPanel centerPanel;
	//constructor
	public Add_Customer()
	{
		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(20, 30, 30, 30));
		this.setLayout(new BorderLayout() );
			
		/***********************************************************************************/
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(9,2,30,30) );
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
			countryArray = new DbConnection().getAllCountry();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
//create inner class listener object
	private class CountryDetails implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				@SuppressWarnings("unchecked")
				String calledBy =((JComboBox<String>)e.getSource()).getName();
				try
				{
					if(calledBy.equals("country")) {				
							districtArray = new DbConnection().getAllDistrictInCountry(countryFld.getSelectedItem().toString());
							districtFld.removeAllItems();
							for(String district:districtArray)
								districtFld.addItem(district);
							districtFld.setEnabled(true);
							resetComboBoxBorder(cityFld,districtFld,countryFld);
						
					}
					else if(calledBy.equals("district")) {				
						cityArray = new DbConnection().getAllCitiesInDistrict(districtFld.getSelectedItem().toString());
						cityFld.removeAllItems();
							for(String city:cityArray)
								cityFld.addItem(city);
							cityFld.setEnabled(true);
					
					}
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}
		}
	}//end inner class
	
	
//create inner class listener object
	private class AddCustomerPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add"))
			{			  
				if(areTextFieldValid(firstNameFld,emailFld,  lastNameFld, addressFld,postalFld,phoneFld) &&
						areComboBoxValid(cityFld,districtFld,countryFld)) {

//					try
//					{
//						new DbConnection();
//					
//					} catch (SQLException e1)
//					{
//						System.out.println("SQL Exeption, message is: " + e1.getMessage());
//					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter the value in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}else if(e.getActionCommand().equals("Clear"))
				clearPanel(centerPanel);
			
		}//end actionPerformed()
	
	}//end inner class
}