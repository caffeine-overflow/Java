package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Add_Customer extends JPanel
{
	JButton addCustomerBtn, clearCustomerAdd;
	JTextField firstNameFld,emailFld,  lastNameFld, addressFld, cityFld,postalFld, phoneFld, districtFld, countryFld;
	//constructor
	public Add_Customer()
	{
		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(20, 30, 30, 30));
		this.setLayout(new BorderLayout() );
			
		/***********************************************************************************/
		JPanel centerPanel = new JPanel();
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
		
		firstNameFld = new JTextField();
		lastNameFld = new JTextField();
		phoneFld  = new JTextField();
		emailFld = new JTextField();
		cityFld = new JTextField();
		postalFld = new JTextField();
		districtFld = new JTextField();
		addressFld = new JTextField();
		countryFld = new JTextField();
		
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
				
		/***********************************************************************************/
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPanel.setLayout(new FlowLayout() );
	
		this.add(btnPanel, BorderLayout.SOUTH);
		
		addCustomerBtn = new JButton("Add");
		clearCustomerAdd = new JButton("Clear");
		
		btnPanel.add(addCustomerBtn);
		btnPanel.add(clearCustomerAdd);
		
	}	
}