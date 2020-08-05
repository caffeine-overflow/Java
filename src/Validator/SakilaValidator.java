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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.mysql.cj.util.StringUtils;

import Exception.SakilaExpection;
import Model.Actor;
import Model.Address;
import Model.Customer;
import static Validator.StringPredicates.*;
public class SakilaValidator
{
	
	//Source for REGEX  https://howtodoinjava.com/java/regex/
	final private static String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	final private static String PHONE_REGEX = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
	
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
	
	
	public static void validateCustomer(Customer customer) throws SakilaExpection {
		ArrayList<String> errors= new ArrayList<>();
  	validateAddress(customer.getAddress(),errors);
  	validateRequest(lengthIsGreaterThan(1).and(lengthIsLessThan(45)),"First Name",errors,customer.getFirstName());
  	validateRequest(lengthIsGreaterThan(1).and(lengthIsLessThan(45)),"Last Name",errors,customer.getLastName());
  	validateRequest(lengthIsLessThan(50).and(followsRegesPatternOf(Pattern.compile(EMAIL_REGEX))),"Email",errors,customer.getEmail());
  	checkErrors(errors);
	}
	
	public static boolean validateAddress(Address address,List<String> errors) {
		validateRequest(followsRegesPatternOf(Pattern.compile(PHONE_REGEX)),"Phone",errors,address.getPhone());
  	validateRequest(lengthIsGreaterThan(5).and(lengthIsLessThan(50)),"Street Address",errors,address.getStreetAddress());
  	validateRequest(lengthIsBetween(5,7),"Postal Code",errors,address.getPostalCode());

		return false;
	}
	
	public static void validateActor(Actor actor) throws SakilaExpection {
		ArrayList<String> errors= new ArrayList<>();
  	validateRequest(lengthIsGreaterThan(1).and(lengthIsLessThan(45)),"First Name",errors,actor.getFirstName());
  	validateRequest(lengthIsGreaterThan(1).and(lengthIsLessThan(45)),"Last Name",errors,actor.getLastName());
  	checkErrors(errors);
	}
	
	
	public static <T> boolean validateRequest(final ValidationPredicate<T> predicate,final String key,final List<String> errorList,T value) {
		if (value==null) return false;
		boolean test = predicate.test(value);
		if(!test) {
			errorList.add(String.format("%s is invalid %s",key,getErrors(predicate)));
		}
		return test;
	}
	
	private static String getErrors(ValidationPredicate<?> predicate)
	{
		return predicate.getErrors().isEmpty() ? "" : String.format("%s", StringUtils.joinWithSerialComma(predicate.getErrors()));
	}
	
	private static void checkErrors(final List<String> errors) throws SakilaExpection {
		if(!errors.isEmpty())
			throw new SakilaExpection(errors);
	}
}
