/**
 * Program Name	: Add_Actor.java
 * Purpose			: A panel to add a new actor
 * Author				: Prabin Gyawali (0877282) and Danish Davish (0691688)
 * Date					: Aug. 2, 2020
 */

package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static Database.DbConnection.*;
import Model.Actor;
import static Validator.SakilaValidator.*;

@SuppressWarnings("serial")
public class Add_Actor extends JPanel {

	//declare required components
	private JButton addActorBtn, clearActorBtn;
	private JTextField actorFirstNameFld, actorLastNameFld;
	private JPanel centerPanel;
	// constructor
	public Add_Actor() {
		super();
		/***********************************************************************************/
		//initilize and add the components
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel = new JPanel();
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
		addActorBtn.addActionListener(new AddActorPage());
		btnPanel.add(addActorBtn);
		btnPanel.add(clearActorBtn);
	}


	/**
	 * Action listener for Add Actor Page
	 */
	private class AddActorPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add")) //If Add button is clicked
			{			  
				//validate all the text fields
				if(areTextFieldValid(actorFirstNameFld,actorLastNameFld)) {
					//create a actor object and get the information.
					Actor actor = new Actor();
					actor.setFirstName(actorFirstNameFld.getText());
					actor.setLastName(actorLastNameFld.getText()); 
					try
					{
						validateActor(actor); //validate the actor object
						//Insert the actor
						if(insertActor(actor)==-2)
							JOptionPane.showMessageDialog(null, "Mr. "+actor.getFirstName()+" already exists in our database", "Warning", JOptionPane.WARNING_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "Successfully inserted Mr. "+actor.getFirstName(), "Success", JOptionPane.INFORMATION_MESSAGE);
						clearPanel(centerPanel);

					}  catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter the value in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}else if(e.getActionCommand().equals("Clear")) //if clear button clicked, clear the panel
				clearPanel(centerPanel);

		}//end actionPerformed()

	}//end inner class
}