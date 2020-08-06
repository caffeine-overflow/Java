/**
 * Program Name	: Rental.java
 * Purpose			: .................
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 5, 2020
 */
package Model;

public class Rental
{
	int filmID;
	int inventoryID;
	int customerID;
	int rentalDuration;
	/**
	 * @return the filmID
	 */
	public int getFilmID()
	{
		return filmID;
	}
	/**
	 * @param filmID the filmID to set
	 */
	public void setFilmID(int filmID)
	{
		this.filmID = filmID;
	}
	/**
	 * @return the inventoryID
	 */
	public int getInventoryID()
	{
		return inventoryID;
	}
	/**
	 * @param inventoryID the inventoryID to set
	 */
	public void setInventoryID(int inventoryID)
	{
		this.inventoryID = inventoryID;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID()
	{
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
	}
	/**
	 * @return the rentalDuration
	 */
	public int getRentalDuration()
	{
		return rentalDuration;
	}
	/**
	 * @param rentalDuration the rentalDuration to set
	 */
	public void setRentalDuration(int rentalDuration)
	{
		this.rentalDuration = rentalDuration;
	}
}

