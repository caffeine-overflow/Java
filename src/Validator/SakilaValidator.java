/**
 * Program Name	: SakilaValidator.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 4, 2020
 */
package Validator;

import java.awt.Color;

import javax.swing.BorderFactory;
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
}
