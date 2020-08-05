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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.mysql.cj.util.StringUtils;

import Exception.SakilaExpection;
import Model.Actor;
import Model.Address;
import Model.Customer;
import Model.Film;

import static Validator.StringPredicates.*;
public class SakilaValidator
{
	
	//Source for REGEX  https://howtodoinjava.com/java/regex/
	final private static String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	final private static String PHONE_REGEX = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
	final private static List<String> SPECIAL_FEATURE_LIST = Arrays.asList("Trailers","Commentaries","Deleated Scenes","Behind the Scenes");
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
			if(comboBox.getSelectedItem()==null || comboBox.getSelectedItem().toString().isBlank()) {
				comboBox.setBorder(border);
				isValid=false;
			}
		}
		return isValid;
	}
	
	public static boolean areTextAreasValid(JTextArea... textAreas)
	{
		boolean isValid=true;
		//create a border with red color and width 2 unit
			Border border = BorderFactory.createLineBorder(Color.RED, 2);	
			for(JTextArea textarea : textAreas) {
						
				//First reset the border color for both the text field
				textarea.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextArea.border"));
				//Change the border color of the text field if it is blank 
				if(textarea.getText().isBlank()) {
					textarea.requestFocus();
					textarea.setBorder(border);
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
			if(!(component instanceof JLabel ))
			((JComponent) component).setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
      if (component instanceof JTextField) {
          ((JTextField) component).setText("");
      }
      else if(component instanceof JComboBox) {
      	((JComboBox) component).setSelectedIndex(-1);
      }
      else if(component instanceof JTextArea) {
      	((JTextArea) component).setText("");
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
	
	
	public static void validateFilm(Film film) throws SakilaExpection {
		ArrayList<String> errors= new ArrayList<>();
  	validateRequest(lengthIsLessThan(127),"Title ",errors,film.getTitle());
  	validateRequest(lengthIsGreaterThan(10),"Description ",errors,film.getDescription());
  	validateRequest(lengthIsBetween(1,24),"Category ",errors,film.getCategory());
  	validateRequest(lengthIsBetween(1,19),"Language ",errors,film.getLanguage());
  	
  	if(validateSpecialFeature(errors,film.getSpecial_features()))
  		validateRequest(hasDuplicateValues(),"Special Feature ",errors, Arrays.asList(film.getSpecial_features().split(",")));
  	
  	//Store doesnot have anything before 1833
  	if(validateRequest(isValidNumber().and(valueIsBetween(1833,2020)),"Release Year ",errors,film.getStringRelease_year()))
  		film.setRelease_year(Integer.parseInt(film.getStringRelease_year()));
  	
  	if(validateRequest(isValidNumber().and(valueIsBetween(5,1200)),"Film Length "+film.getStringLength(),errors,film.getStringLength()))
  		film.setLength(Integer.parseInt(film.getStringLength()));
  	
  	//Convert the int to double inside the string.
  	String replacementCost =film.getStringReplacement_cost();
  	if(isValidNumber().test(replacementCost)) replacementCost+=".0";
  	
  	//Minimum replacement cost is $5 and maximum replacement cost is $100
  	if(validateRequest(isValidDouble().and(valueIsBetween(5.0,100.0)),"Replacement Cost "+film.getStringReplacement_cost(),errors,film.getStringReplacement_cost()))
  		film.setReplacement_cost(Float.parseFloat(film.getStringReplacement_cost()));
  	
  	checkErrors(errors);
	}


	private static boolean validateSpecialFeature(ArrayList<String> errors, String special_features)
	{
		int count=0;
		boolean toReturn=true;
		for(String feature:special_features.split(",")) {
			if(!validateRequest(isInTheList(SPECIAL_FEATURE_LIST),"Special Feature["+count+++"] / "+feature,errors,feature))
				toReturn=false;
		}
		return toReturn;
		
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
