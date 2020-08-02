/**
 * Program Name	: ValidationPredicate.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public interface ValidationPredicate<T> extends Predicate<T>
{
		default List<String> getErrors(){return new ArrayList<>(2);}
		
		default ValidationPredicate<T> and(ValidationPredicate<? super T> other)
		{
				Objects.requireNonNull(other);
				return new ValidationPredicate<T>() {
					List<String> errors = new ArrayList<>();
					@Override
					public List<String> getErrors(){
						return new ArrayList<>(errors);
					}
					
					@Override
					public boolean test(final T t)
					{
						ValidationPredicate<T> outer = ValidationPredicate.this;
						boolean didThisPass = outer.test(t);
						if(didThisPass) {
							boolean didOtherPass = other.test(t);
							if(!didOtherPass) {
									errors.addAll(other.getErrors());
									return false;
							}
						}
						else {
							errors.addAll(outer.getErrors());
							return false;
						}
						return true;
					}					
				};
		}
		
		
		default ValidationPredicate<T> or(ValidationPredicate<? super T> other)
		{
				Objects.requireNonNull(other);
				return new ValidationPredicate<T>() {
					List<String> errors = new ArrayList<>();
					@Override
					public List<String> getErrors(){
						return new ArrayList<>(errors);
					}
					
					@Override
					public boolean test(final T t)
					{
						ValidationPredicate<T> outer = ValidationPredicate.this;
						boolean didThisPass = outer.test(t);
						if(!didThisPass) {
							boolean didOtherPass = other.test(t);
							if(!didOtherPass) {
									errors.addAll(other.getErrors());
									return false;
							}
						}
						return true;
					}					
				};
		}		
		
}
