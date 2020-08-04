package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import Database.DbConnection;
import Model.Actor;
import static Validator.SakilaValidator.*;

public class Add_Actor extends JPanel {
	JButton addActorBtn, clearActorBtn;
	JTextField actorFirstNameFld, actorLastNameFld;

	// constructor
	public Add_Actor() {
		super();
		this.setBorder(new EmptyBorder(20, 10, 30, 10));

		/***********************************************************************************/
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2, 30, 30));
		this.add(centerPanel);

		JLabel actorFirstNameLbl = new JLabel("First Name:");
		actorFirstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel actorLastNameLbl = new JLabel("Last Name:");
		actorLastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);


		actorFirstNameFld = new JTextField();
		actorLastNameFld = new JTextField();

		centerPanel.add(actorFirstNameLbl);
		centerPanel.add(actorFirstNameFld);
		centerPanel.add(actorLastNameLbl);
		centerPanel.add(actorLastNameFld);

		/***********************************************************************************/
		JPanel btnPanel = new JPanel();

		btnPanel.setLayout(new FlowLayout());

		this.add(btnPanel);

		addActorBtn = new JButton("Add");
		clearActorBtn = new JButton("Clear");
		AddActorPage aap = new AddActorPage();
		addActorBtn.addActionListener(aap);
		btnPanel.add(addActorBtn);
		btnPanel.add(clearActorBtn);
	}
	
	
//create inner class listener object
	private class AddActorPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add"))
			{			  
				if(areTextFieldValid(actorFirstNameFld,actorLastNameFld)) {
					Actor actor = new Actor();
					actor.setFirstName(actorFirstNameFld.getText());
					actor.setLastName(actorLastNameFld.getText());
					try
					{
						new DbConnection().insertActor(actor);
					
					} catch (SQLException e1)
					{
						System.out.println("SQL Exeption, message is: " + e1.getMessage());
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter the value in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}//end actionPerformed()
	
	}//end inner class
}