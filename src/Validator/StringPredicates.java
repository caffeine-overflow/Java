/**
 * Program Name	: StringPredicates.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mysql.cj.util.StringUtils;


public class StringPredicates
{
	

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
	
	public static ValidationPredicate<String> isValidPhoneNumber(){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
					Arrays.asList(String.format("The value entered is not a valid phone number ")));
				}

			@Override
			public boolean test(String t)
			{
				return followsRegesPatternOf(Pattern.compile("")).test(t);
			}			
		};
	}
}
