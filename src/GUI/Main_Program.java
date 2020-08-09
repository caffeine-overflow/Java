/**
 * Program Name	: Main_Program.java
 * Purpose			: Main program Sakila App which contains the main function
 * Group Members: Prabin Gyawali (0877282) and Danish Davish (0691688) and Rohit Butta (0908402)
 * Date					: Aug. 2, 2020
 */
package GUI;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main_Program extends JFrame
{

	//main frame for the sakila app
	public Main_Program() {
		super("Sakila DVD Rental");

		//boilerplate
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,650);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		//create a JTabbedPane object
		JTabbedPane tPane = new JTabbedPane();

		tPane.add("Add new customer", new Add_Customer());
		tPane.add("Add new actor", new Add_Actor());
		tPane.add("Add new film", new Add_Film());
		tPane.add("Add new rental", new Add_Rental());
		tPane.add("Reports", new Reports());


		//add the JTabbedPane to the JFrame
		this.add(tPane);

		//this.pack();
		//last line
		this.setVisible(true);
	}

	//Main entry of the program
	public static void main(String[] args)
	{
		//call the main program
		new Main_Program();

	}
}

