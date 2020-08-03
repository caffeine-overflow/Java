/**
 * Program Name	: Customer.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Model;

public class Customer
{
	String lastName;
	String firstName;
	String email;
	Store store;
	Address address;
	Boolean active;
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
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	/**
	 * @return the store
	 */
	public Store getStore()
	{
		return store;
	}
	/**
	 * @param store the store to set
	 */
	public void setStore(Store store)
	{
		this.store = store;
	}
	/**
	 * @return the address
	 */
	public Address getAddress()
	{
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address)
	{
		this.address = address;
	}
	/**
	 * @return the active
	 */
	public Boolean getActive()
	{
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active)
	{
		this.active = active;
	}
	
	
}
