/**
 * Program Name	: Inventory.java
 * Purpose			: A POJO to store Inventory object
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 2, 2020
 */
package Model;

public class Inventory
{
	Film film;
	Store store;
	/**
	 * @return the film
	 */
	public Film getFilm()
	{
		return film;
	}
	/**
	 * @param film the film to set
	 */
	public void setFilm(Film film)
	{
		this.film = film;
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

}
