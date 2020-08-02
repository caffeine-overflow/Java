package GUI;
/**
 * Program Name: IntroPanel.java
 * Purpose: a class to construct a JPanel that will later be called in another 
 *          class that will add it to a JTabbedPane container. 
 *          This class will contain instructions for the user and will be 
 *          the "top tab" in the layer of tabs. 
 * Coder: Bill Pulling for Section 01
 * Date: Feb 28, 2020
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Reports extends JPanel
{
	//constructor
	public Reports()
	{
		super();//courtesy call
		this.setBackground(Color.GREEN);
		this.setLayout(new FlowLayout());
		
		//two JLabels
		JLabel lbl1 = new JLabel("Layout Manager Demonstration App");
		JLabel lbl2 = new JLabel("Choose a tab to see that particular layout.");
		
		this.add(lbl1);
		this.add(lbl2);		
	}	//end constructor
}
//end class