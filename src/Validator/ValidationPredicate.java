/**
 * Program Name	: ValidationPredicate.java
 * Purpose			: ValidationPredicate class which inherit from Predicate
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

	//short circuited and predicate function. 
	/**
	 * checks this predicate and the other predicate which is the argument.
	 * if this predicate fails than the other predicate is not evaluated
	 * @return
	 */
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

	/**
	 * checks this predicate or the other predicate which is the argument.
	 * if this predicate returns true than the other predicate is not evaluated
	 * @return
	 */
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
