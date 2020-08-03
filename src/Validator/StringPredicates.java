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
					Arrays.asList(String.format("The value does not matches the pattern: %s",pattern.pattern())));
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
					Arrays.asList(String.format("The string has an empty value.")));
				}

			@Override
			public boolean test(final String value)
			{
				return StringUtils.isNullOrEmpty(value.trim());
			}			
		};
	}
}
