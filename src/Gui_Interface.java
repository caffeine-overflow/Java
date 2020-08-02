
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui_Interface
{

	public static void main(String[] args)
	{
		// build the JFrame here in the main 
		JFrame frame = new JFrame("Sakila DVD Rental");
		
		//boilerplate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,800);
		frame.setLocationRelativeTo(null);
		
		//create a JTabbedPane object
		JTabbedPane tPane = new JTabbedPane();
		
		tPane.add("Add new customer", new Add_Customer());
		tPane.add("Add new actor", new Add_Actor());
		tPane.add("Add new film", new Add_Film());
		tPane.add("Add new rental", new Add_Rental());
		tPane.add("Reports", new Reports());
		
		
		//add the JTabbedPane to the JFrame
		frame.add(tPane);
		
		frame.pack();
		//last line
		frame.setVisible(true);
		
	}
}

