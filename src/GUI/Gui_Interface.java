package GUI;

import javax.swing.*;

@SuppressWarnings("serial")
public class Gui_Interface extends JFrame
{

	public Gui_Interface() {
	// build the JFrame here in the main 
			super("Sakila DVD Rental");
			
			//boilerplate
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(600,700);
			this.setLocationRelativeTo(null);
			
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
	public static void main(String[] args)
	{
		new Gui_Interface();
		
	}
}

