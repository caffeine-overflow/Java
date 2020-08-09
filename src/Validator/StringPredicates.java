/**
 * Program Name	: StringPredicates.java
 * Purpose			: Class containing predicate functions to test String objects
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mysql.cj.util.StringUtils;


public class StringPredicates
{
	/**
	 * Validates if the string to test is in the list of values
	 */
	public static ValidationPredicate<String> isInTheList(final List<String> values){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format("The value must be one of [%s]",values.stream()
								.collect(Collectors.joining(", ")))));
			}

			@Override
			public boolean test(String t)
			{
				return values.stream().anyMatch(val -> val.equals(t));
			}			
		};
	}

	/**
	 * Validates if the list has any duplicate values
	 */
	public static ValidationPredicate<List<?>> hasDuplicateValues(){
		return new ValidationPredicate<List<?>>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format("The List contains duplicate values ")));
			}

			@Override
			public boolean test(List<?> t)
			{
				return t.size()==new HashSet<>(t).size();
			}			
		};
	}

	/**
	 * Validates if the string to test follows the certain pattern
	 */
	public static ValidationPredicate<String> followsRegesPatternOf(final Pattern pattern){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList("The value does not matches the pattern"));
			}
			@Override
			public boolean test(final String value)
			{
				return pattern.matcher(value).matches();
			}			
		};
	}

	/**
	 * Validates if the string to test is a valid number
	 */
	public static ValidationPredicate<String> isValidNumber(){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList("The value is not a valid number"));
			}

			@Override
			public boolean test(final String value)
			{
				return Pattern.compile("^\\d+$").matcher(value).matches();
			}			
		};
	}

	/**
	 * Validates if the string to test is the valid double value
	 */
	public static ValidationPredicate<String> isValidDouble(){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList("The value is not a valid floating point"));
			}			
			@Override
			public boolean test(final String value)
			{
				return Pattern.compile("\\d+\\.{0,1}(\\d+)?").matcher(value).matches();
			}			
		};
	}

	/**
	 * Validates if the string to test is not blank
	 */
	public static ValidationPredicate<String> isNotBlank(){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format("The value is blank.")));
			}

			@Override
			public boolean test(final String value)
			{
				return StringUtils.isNullOrEmpty(value.trim());
			}			
		};
	}

	/**
	 * Validates if the length of the string to test is greater than the passed value
	 */
	public static ValidationPredicate<String> lengthIsGreaterThan(final int length){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" must be more than %d characters long",length)));
			}

			@Override
			public boolean test(final String value)
			{
				return value.length()>length;
			}			
		};
	}

	/**
	 * Validates if the length of the string to test is less than the passed value
	 */
	public static ValidationPredicate<String> lengthIsLessThan(final int length){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" must be less than %d characters long",length)));
			}
			@Override
			public boolean test(final String value)
			{
				return value.length()<length;
			}			
		};
	}

	/**
	 * Validates if the length of the string to test is equal to the passed value
	 */
	public static ValidationPredicate<String> lengthIsEqualTo(final int length){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" must be %d characters long",length)));
			}
			@Override
			public boolean test(final String value)
			{
				return value.length()==length;
			}			
		};
	}

	/**
	 * Validates if the length of the string to test is between than the passed values
	 */
	public static ValidationPredicate<String> lengthIsBetween(final int min,final int max){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" must be between %d and %d characters long",min,max)));
			}
			@Override
			public boolean test(final String value)
			{
				return value.length()>=min && value.length()<=max;
			}			
		};
	}

	/**
	 * Validates if the value of the int converted string to test is between than the passed values
	 */
	public static ValidationPredicate<String> valueIsBetween(final int min,final int max){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" value must be between %d and %d",min,max)));
			}
			@Override
			public boolean test(final String value)
			{
				return Integer.parseInt(value)>=min && Integer.parseInt(value)<=max;
			}			
		};
	}

	/**
	 * Validates if the value of the double converted string to test is between than the passed values
	 */
	public static ValidationPredicate<String> valueIsBetween(final double min,final double max){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
						Arrays.asList(String.format(" value must be between %.2f and %.2f",min,max)));
			}

			@Override
			public boolean test(final String value)
			{
				boolean returnValue=Double.parseDouble(value)>=min &&  Double.parseDouble(value)<=max;
				System.out.println(returnValue);
				return returnValue;
			}			
		};
	}
}
