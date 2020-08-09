/**
 * Program Name	: Actor.java
 * Purpose			: A POJO to store Actor object
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Model;

public class Actor
{
	String firstName;
	String lastName;

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}