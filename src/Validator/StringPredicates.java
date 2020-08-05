/**
 * Program Name	: StringPredicates.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.mysql.cj.util.StringUtils;


public class StringPredicates
{
	

	public static ValidationPredicate<String> isInTheList(final List<String> values){
		return new ValidationPredicate<String>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
					Arrays.asList(String.format("The value entered is not in the list ")));
				}

			@Override
			public boolean test(String t)
			{
				return values.stream().anyMatch(val -> val.equals(t));
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
