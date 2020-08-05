package GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Add_Rental extends JPanel
{
	JButton addentalBtn, clearRentalAdd;
	JTextField returnDateFld, rentalDateFld;
	JComboBox cutomerNameFld ,filmTitleFld ;
	JPanel centerPanel;
	
	public Add_Rental() {
			
		super();
		this.setBackground(new Color(255,255,255));
		this.setBorder(new EmptyBorder(20, 30, 30, 30));
		this.setLayout(new BorderLayout() );
			
		/***********************************************************************************/
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(5,2,30,30) );
		this.add(centerPanel, BorderLayout.CENTER);
		
		JLabel customerNameLbl = new JLabel("Customer Name:");
		customerNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel filmTitleLbl = new JLabel("Fim Title:");
		filmTitleLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel rentalDateLbl = new JLabel("Rental Date:");		
		rentalDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel returnDateLbl = new JLabel("Return Date:");		
		returnDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		String [] arr = {"1","2"};
		
		cutomerNameFld = new JComboBox(arr);
		filmTitleFld = new JComboBox(arr);
		rentalDateFld  = new JTextField();
		returnDateFld = new JTextField();
		
	
		centerPanel.add(customerNameLbl);
		centerPanel.add(filmTitleFld);
		centerPanel.add(filmTitleLbl);
		centerPanel.add(cutomerNameFld);
		centerPanel.add(rentalDateLbl);
		centerPanel.add(rentalDateFld);	
		centerPanel.add(returnDateLbl);
		centerPanel.add(returnDateFld);	


		/***********************************************************************************/
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPanel.setLayout(new FlowLayout() );
	
		this.add(btnPanel, BorderLayout.SOUTH);
		
		addentalBtn = new JButton("Add");
		clearRentalAdd = new JButton("Clear");
		btnPanel.add(addentalBtn);
		btnPanel.add(clearRentalAdd);
	}	
}
