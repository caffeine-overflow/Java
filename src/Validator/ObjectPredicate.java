/**
 * Program Name	: ObjectPredicate.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 4, 2020
 */
package Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ObjectPredicate
{
	
	public static ValidationPredicate<Object> isNotNull(){
		return new ValidationPredicate<Object>() {
			@Override
			public List<String> getErrors(){
				return new ArrayList<>(
					Arrays.asList(String.format("The object is null")));
				}

			@Override
			public boolean test(final Object value)
			{
				return value!=null;
			}			
		};
	}
}
