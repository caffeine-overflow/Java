/**
 * Program Name	: SakilaValidator.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 4, 2020
 */
package Validator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class SakilaValidator
{
	public static boolean areTextFieldValid(JTextField... textfields) {
		boolean isValid=true;
		for(JTextField textField : textfields) {
		//create a border with red color and width 2 unit
			Border border = BorderFactory.createLineBorder(Color.RED, 2);				
			//First reset the border color for both the text field
			textField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		//Change the border color of the text field if it is blank 
			if(textField.getText().isBlank()) {
				textField.requestFocus();
				textField.setBorder(border);
				isValid=false;
			}
		}
		return isValid;
	}
	
	@SafeVarargs
	public static boolean areComboBoxValid(JComboBox<String>... comboBoxes) {
		boolean isValid=true;
	//create a border with red color and width 2 unit
		Border border = BorderFactory.createLineBorder(Color.RED, 2);	
		for(JComboBox<String> comboBox : comboBoxes) {
					
			//First reset the border color for both the text field
			comboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
			//Change the border color of the text field if it is blank 
			if(comboBox.getSelectedItem()==null) {
				comboBox.setBorder(border);
				isValid=false;
			}
		}
		return isValid;
	}
	
	@SafeVarargs
	public static void resetComboBoxBorder(JComboBox<String>... comboBoxes) {
		for(JComboBox<String> comboBox : comboBoxes) {		
			//First reset the border color for both the text field
			comboBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

		}
	}
	
	public static void clearPanel(Container container) {
		for (Component component : container.getComponents()) {
      if (component instanceof JTextField) {
          ((JTextField) component).setText("");
      }
      else if(component instanceof JComboBox) {
      	((JComboBox) component).setSelectedIndex(-1);
      }
      else if(component instanceof JCheckBox) {      	
      	((JCheckBox) component).setSelected(false);
      }
      else if(component instanceof Container) {      	
      	clearPanel((Container)component);
      }
  }
	}
}
