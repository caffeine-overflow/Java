/**
 * Program Name	: Store.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Model;

public class Store
{
	Staff manager;
	Address address;
	/**
	 * @return the manager
	 */
	public Staff getManager()
	{
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(Staff manager)
	{
		this.manager = manager;
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
	
}
