package Database;
/**
 * Program Name: DbConn.java
 * Purpose: shows the steps involved in making a connection to a back end database
 *          using a JDBC driver and classes from the java.sql package.
 *          
 * Coder: Prabin Gyawali (0877282)
 * Date: Aug 2, 2020
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.*;

public class DbConnection
{

	//Connection, Statement and Result set object to be accessed from all the functions
	private static Connection myConn = null;
	private static Statement myStmt = null;
	private static ResultSet myRslt = null;
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	// make the constructor private so that it couldn't be instantiated.
	private DbConnection() {}

	//Private function that initializes the connection and statement object
	private static void getConnection() throws SQLException {
		//create a Connection object by calling a static method of DriverManager class
		myConn = DriverManager.getConnection(CONNECTION_STRING,"root","password");

		//Step 2: create a Statement object by calling a method of the Connection object
		myStmt = myConn.createStatement();
	}

	//static function to close connection, statement and result set object
	private static void closeConnection() throws SQLException {
		if(myRslt != null)
			myRslt.close();
		if(myStmt != null)
			myStmt.close();
		if(myConn != null)
			myConn.close();		
	}

	/**
	 * 
	 * @param city , a string representing name of city
	 * @param district, a string representing name of district
	 * @return list of address for the given city in the district
	 * @throws SQLException
	 */
	public static List<Address> getAllAddressForCityAndDistrict(String city,String district) throws SQLException{
		List<Address> allAddress = new ArrayList<Address>();
		try {
			//get the connection
			getConnection();
			//select all the required attribute for address
			myRslt = myStmt.executeQuery("Select "
					+ "addr.address, addr.district, c.city, addr.postal_code, addr.phone "
					+ "from address addr inner join city c on addr.city_id=c.city_id "
					+ "and c.city = '"+city+"' "
					+ "and addr.district = '"+district+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				//get the address from result set
				Address address = new Address();
				address.setStreetAddress(myRslt.getString("address"));
				address.setDistrict(myRslt.getString("district"));
				address.setCity(myRslt.getString("city"));
				address.setCountry(myRslt.getString("country"));
				address.setPhone(myRslt.getString("phone"));
				address.setPostalCode(myRslt.getString("postal_code"));

				//add the address to the return list
				allAddress.add(address);
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection(); 
		}
		return allAddress;

	}

	/**
	 * 
	 * @return list of all countries 
	 * @throws SQLException
	 */
	public static Vector<String> getAllCountry() throws SQLException{
		Vector<String> allCountry = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select country from country");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allCountry.add(myRslt.getString("country"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allCountry;

	}


	/**
	 * 
	 * @return all the film category
	 * @throws SQLException
	 */
	public static Vector<String> getAllCategory() throws SQLException{
		Vector<String> categories = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select name from category");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				categories.add(myRslt.getString("name"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return categories;
	}


	/**
	 * 
	 * @return all the film languages
	 * @throws SQLException
	 */
	public static Vector<String> getAllLanguages() throws SQLException{
		Vector<String> languages = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select name from language");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				languages.add(myRslt.getString("name"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return languages;
	}

	/**
	 * 
	 * @return all the customers, a string which contains the customer first and last name concatenated 
	 * 					along with email and ID
	 * @throws SQLException
	 */
	public static Vector<String> getAllCustomer() throws SQLException{
		Vector<String> customers = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("select customer_id,first_name, last_name,email from customer");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				customers.add(concatTitleCaseString(myRslt.getString("first_name"),myRslt.getString("last_name")) +" ("+myRslt.getString("email")+")    @id::"+myRslt.getString("customer_id") );
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return customers;
	}


	/**
	 * 
	 * @param countryName
	 * @return list of district in a country
	 * @throws SQLException
	 */
	public static Vector<String> getAllDistrictInCountry(String countryName) throws SQLException{
		Vector<String> allDistrict = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct district from address inner join city "
					+ "on address.city_id=city.city_id inner join country "
					+ "on city.country_id=country.country_id and country = '"+countryName+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allDistrict.add(myRslt.getString("district"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allDistrict;

	}


	/**
	 * 
	 * @return list of string representing film titles with their respective ids
	 * @throws SQLException
	 */
	public static Vector<String> getAllFilmTitles() throws SQLException{
		Vector<String> allFilmTitles = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select * from film");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				//convert the movie to title case and adds id to it.
				allFilmTitles.add(concatTitleCaseString(myRslt.getString("title").split(" "))+"    @id::"+myRslt.getString("film_id"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allFilmTitles;
	}

	/**
	 * 
	 * @return all the movie titles
	 * @throws SQLException
	 */
	public static Vector<String> getAllFilms() throws SQLException{
		Vector<String> allFilmTitles = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select * from film");
			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allFilmTitles.add(myRslt.getString("title"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allFilmTitles;
	}


	/**
	 * 
	 * @return a list of actors first and last name concatenated 
	 * @throws SQLException
	 */
	public static Vector<String> getAllActors() throws SQLException{
		Vector<String> allActors = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select first_name,last_name from actor");
			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allActors.add(concatTitleCaseString(myRslt.getString("first_name"),myRslt.getString("last_name")));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allActors;

	}


	/**
	 * 
	 * @param filmID
	 * @return get the rental duration for certain movie.
	 * @throws SQLException
	 */
	public static int getRentalDurationForFilm(int filmID) throws SQLException {
		int filmDUration=-1;
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select rental_duration from film where film_id = '"+filmID+"'");		  
			//Step 4: PROCESS the myRslt result set object using a while loop
			myRslt.next();
			filmDUration= myRslt.getInt("rental_duration");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return filmDUration;
	}

	/**
	 * 
	 * @param whereClause, the case to query the database
	 * @return a float value representing the total income based upon the whereClause
	 * @throws SQLException
	 */
	public static float getIncomeReport(String whereClause) throws SQLException {
		float filmDUration=-1f;
		try {
			getConnection();
			myRslt = myStmt.executeQuery("select distinct sum(amount) as amount from payment inner join"
					+ " rental on payment.rental_id = rental.rental_id inner join"
					+	" inventory on rental.inventory_id = inventory.inventory_id inner join"
					+	" film on inventory.film_id = film.film_id inner join "
					+	" store on inventory.store_id = store.store_id inner join"
					+	" film_category on film_category.film_id = film.film_id inner join"
					+	" category on category.category_id = film_category.category_id"
					+ whereClause);		  
			//Step 4: PROCESS the myRslt result set object using a while loop
			myRslt.next();
			filmDUration= myRslt.getFloat(1);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return filmDUration;
	}

	/**
	 * 
	 * @param whereClause, the case to query the database
	 * @return a string representing  best customers based upon money spent and total rentals
	 * @throws SQLException
	 */
	public static String getBestCustomer(String whereClause) throws SQLException {
		String returnValue="";
		try {
			getConnection();
			//Query representing the best customer with hisgest rental
			myRslt = myStmt.executeQuery("select first_name,last_name, Total_Rental from"
					+ " customer inner join (select count(*) as 'Total_Rental',customer_id from rental"
					+ " inner join inventory on rental.inventory_id = inventory.inventory_id"
					+ whereClause
					+ " group by customer_id order by 1 desc limit 1) as rental" 
					+ " on customer.customer_id = rental.customer_id");		  

			myRslt.next();
			returnValue+=concatTitleCaseString(myRslt.getString("first_name"),myRslt.getString("last_name"))+" who rented "+myRslt.getInt("Total_Rental")+" times and\n\t\t";

			//Query representing the best customer with most money spent
			myRslt = myStmt.executeQuery("select first_name,last_name,Total_Spent from"
					+ " customer inner join (select sum(amount) as 'Total_Spent',payment.customer_id from payment"
					+ " inner join rental on payment.rental_id = rental.rental_id"
					+ " inner join inventory on rental.inventory_id = inventory.inventory_id"
					+ whereClause
					+ " group by customer_id order by 1 desc limit 1) as rental" 
					+ " on customer.customer_id = rental.customer_id");	

			myRslt.next();
			returnValue+=concatTitleCaseString(myRslt.getString("first_name"),myRslt.getString("last_name"))
					+" who spent $"+myRslt.getFloat("Total_Spent");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnValue;
	}


	/**
	 * 
	 * @param districtName
	 * @return list of string representing all the cities in a district
	 * @throws SQLException
	 */
	public  static Vector<String> getAllCitiesInDistrict(String districtName) throws SQLException{
		Vector<String> allCities = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct city from address inner join city "
					+ "on address.city_id=city.city_id and district = '"+districtName+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allCities.add(myRslt.getString("city"));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return allCities;

	}

	/**
	 * 
	 * @param city
	 * @param country
	 * @return the city id for certain city in a country
	 * @throws SQLException
	 */
	public static int getCityIdByNameAndCountry(String city,String country) throws SQLException {
		int cityID=-1;
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct city_id from city inner join country on"
					+ " city.country_id=country.country_id and city = '"+city+"' and country = '"+country+"'");		  
			//Step 4: PROCESS the myRslt result set object using a while loop
			myRslt.next();
			cityID= myRslt.getInt("city_id");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return cityID;
	}


	/**
	 * 
	 * @param customer, A customer object
	 * @return true if customer is inserted false if there was some exception
	 * @throws SQLException
	 */
	public static boolean insertCustomer(Customer customer) throws SQLException {
		try {
			Address address = customer.getAddress();
			int cityID = getCityIdByNameAndCountry(address.getCity(),address.getCountry());

			getConnection();
			myConn.setAutoCommit(false);
			myStmt.executeUpdate("INSERT INTO address (address, district, city_id, postal_code, phone,location) "
					+ "VALUES ('"+address.getStreetAddress()+"','" +address.getDistrict()+"','" +cityID+"','" +address.getPostalCode()+"','" 
					+address.getPhone()+"',point(0,0))",
					Statement.RETURN_GENERATED_KEYS);

			myRslt = myStmt.getGeneratedKeys();
			myRslt.next();
			int addressID = myRslt.getInt(1);
			myStmt.executeUpdate("INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) "
					+ "VALUES (1,'"+customer.getFirstName()+"','" +customer.getLastName()+"','" +customer.getEmail()+"','" +addressID+"',1)");
			myConn.commit();
			closeConnection();
			return true;
		}
		catch(Exception ex)
		{
			myConn.rollback();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return false;

	}

	/**
	 * 
	 * @param rental, a rental object
	 * @return true if rental was inserted, false if it was not inserted
	 * @throws SQLException
	 */
	public static boolean insertRental(Rental rental) throws SQLException {
		boolean returnValue=false;
		try {
			getConnection();
			myStmt.executeUpdate("INSERT INTO rental (rental_date, inventory_id, customer_id, return_date, staff_id) "
					+ "VALUES (now(),"+rental.getInventoryID()+"," +rental.getCustomerID()
					+	", DATE_ADD(now(), INTERVAL "+rental.getRentalDuration()+" DAY), 1)");
			returnValue=true;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnValue;

	}

	/**
	 * 
	 * @param actor
	 * @return -1 if any exception occurs, -2 if actor is already in the database, or the actor id if inserted
	 * @throws SQLException
	 */
	public static int insertActor(Actor actor) throws SQLException {
		int returnID=-1;
		try {
			getConnection();	
			myRslt = myStmt.executeQuery("SELECT * FROM actor where first_name = '"+actor.getFirstName()+"' and last_name = '" +actor.getLastName()+"'");
			if(myRslt.next()) return -2;
			myStmt.executeUpdate("INSERT INTO actor (first_name, last_name) "
					+ "VALUES ('"+actor.getFirstName()+"','" +actor.getLastName()+"')", Statement.RETURN_GENERATED_KEYS);
			myRslt = myStmt.getGeneratedKeys();
			if(myRslt.next()) returnID = myRslt.getInt(1);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnID;

	}

	/**
	 * 
	 * @param filmID
	 * @return inventory id if inserted, -1 is error occurs
	 * @throws SQLException
	 */
	public static int insertInventory(int filmID) throws SQLException {
		int returnID=-1;
		try {
			getConnection();	
			myStmt.executeUpdate("INSERT INTO inventory (film_id, store_id) "
					+ "VALUES ("+filmID+",1)", Statement.RETURN_GENERATED_KEYS);
			myRslt = myStmt.getGeneratedKeys();
			if(myRslt.next()) returnID = myRslt.getInt(1);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnID;

	}

	/**
	 * Insert a new film along with its actors
	 * Also insert into junction tables, inventory, film_category and language
	 * @param film
	 * @param actors
	 * @return true if all the insert was successful, false if something fails
	 * @throws SQLException
	 */
	public static boolean insertFilmWithActors(Film film, ArrayList<Actor> actors) throws SQLException {
		boolean returnValue=false;
		try {
			int langID=getLanguageIdByName(film.getLanguage()); //first get the language id
			int catID=getCategoryIdByName(film.getCategory()); //get the category id
			int filmID=-1;
			getConnection();	
			myConn.setAutoCommit(false); //Start the transaction 
			//insert into film
			myStmt.executeUpdate("INSERT INTO film (title, description,release_year,language_id,rental_duration,length,special_features) "
					+ "VALUES ('"+film.getTitle()+"','" +film.getDescription()+"'," +film.getRelease_year()+"," +langID+
					"," +film.getRental_duration()+"," +film.getLength()+",'" +film.getSpecial_features()+"')" , Statement.RETURN_GENERATED_KEYS);
			myRslt = myStmt.getGeneratedKeys();

			if(myRslt.next()) filmID = myRslt.getInt(1);
			//insert into inventory and film_category
			myStmt.executeUpdate("INSERT INTO inventory (film_id, store_id) "
					+ "VALUES ("+filmID+",1)");
			myStmt.executeUpdate("INSERT INTO film_category (film_id, category_id) "
					+ "VALUES ("+filmID+","+catID+")");

			//insert all the actors
			for(Actor actor:actors) {
				int actorId=-1;
				myRslt = myStmt.executeQuery("SELECT * FROM actor where first_name = '"+actor.getFirstName()+"' and last_name = '" +actor.getLastName()+"'");
				if(myRslt.next()) {
					actorId = myRslt.getInt("actor_id");  //if actor already exists in database, just get the actor id
				}
				else {//else insert the actor in database and get the id
					myStmt.executeUpdate("INSERT INTO actor (first_name, last_name) "
							+ "VALUES ('"+actor.getFirstName()+"','" +actor.getLastName()+"')", Statement.RETURN_GENERATED_KEYS);
					myRslt = myStmt.getGeneratedKeys();
					if(myRslt.next()) actorId = myRslt.getInt(1);
				}				
				//insert into film_actor
				myStmt.executeUpdate("INSERT INTO film_actor (actor_id, film_id) "
						+ "VALUES ('"+actorId+"','" +filmID+"')");				
			}
			myConn.commit(); //commit the inserts at the rent
			returnValue=true;
		}
		catch(Exception ex)
		{
			myConn.rollback(); //if some exception occurs, rollback the changes
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnValue;

	}

	/**
	 * 
	 * @param language
	 * @return language id for passed language, if language does not exists, insert a new one and return the id
	 * @throws SQLException
	 */
	public static int getLanguageIdByName(String language) throws SQLException {
		int returnID=-1;
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct language_id from language where name = '"+language+"'");		 
			//Step 4: PROCESS the myRslt result set object using a while loop			
			if(myRslt.next()) {
				returnID= myRslt.getInt("language_id");
			}
			else{
				myStmt.executeUpdate("INSERT INTO language (name) "
						+ "VALUES ('"+language+"')", Statement.RETURN_GENERATED_KEYS);
				myRslt = myStmt.getGeneratedKeys();
				if(myRslt.next()) returnID = myRslt.getInt(1);
			}
			closeConnection();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnID;
	}

	/**
	 * 
	 * @param name
	 * @return category id for passed category, if category does not exists, insert a new one and return the id
	 * @throws SQLException
	 */
	public static int getCategoryIdByName(String name) throws SQLException {
		int returnID=-1;
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct category_id from category where name = '"+name+"'");		  
			//Step 4: PROCESS the myRslt result set object using a while loop
			if(myRslt.next()) {
				returnID= myRslt.getInt("category_id");
			}
			else{
				myStmt.executeUpdate("INSERT INTO language (name) "
						+ "VALUES ('"+name+"')", Statement.RETURN_GENERATED_KEYS);
				myRslt = myStmt.getGeneratedKeys();
				if(myRslt.next()) returnID = myRslt.getInt(1);
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			closeConnection();
		}
		return returnID;
	}

	/**
	 * 
	 * @param values, a list of string
	 * @return convert the passed string to title case and concat them separated by space
	 */
	private static String concatTitleCaseString(String... values) {
		String returnValue="";
		for(String value : values)
			returnValue+=value.toUpperCase().charAt(0) + value.toLowerCase().substring(1) +" ";
		return returnValue.trim();
	}
}
//end class