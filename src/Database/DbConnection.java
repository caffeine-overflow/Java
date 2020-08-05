package Database;
/**
 * Program Name: Driver1a.java
 * Purpose: shows the steps involved in making a connection to a back end database
 *          using a JDBC driver and classes from the java.sql package.
 *          REVISION: changed column labels to column index numbers in the while loop 
 * Coder: Bill Pulling 
 * Date: Jul 9, 2019
 */

//the DYNAMIC DUO
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Model.*;
import javax.sql.*;
public class DbConnection
{

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;
	final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	public DbConnection() {

	}

	private void getConnection() throws SQLException {
		//create a Connection object by calling a static method of DriverManager class
		myConn = DriverManager.getConnection(CONNECTION_STRING,"root","password");

		//Step 2: create a Statement object by calling a method of the Connection object
		myStmt = myConn.createStatement();
	}

	private void closeConnection() throws SQLException {
		if(myRslt != null)
			myRslt.close();
		if(myStmt != null)
			myStmt.close();
		if(myConn != null)
			myConn.close();		
	}

	public List<Address> getAllAddressForCityAndDistrict(String city,String district) throws SQLException{
		List<Address> allAddress = new ArrayList<Address>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select "
					+ "addr.address, addr.district, c.city, addr.postal_code, addr.phone "
					+ "from address addr inner join city c on addr.city_id=c.city_id "
					+ "and c.city = '"+city+"' "
					+ "and addr.district = '"+district+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				Address address = new Address();
				address.setStreetAddress(myRslt.getString("address"));
				address.setDistrict(myRslt.getString("district"));
				address.setCity(myRslt.getString("city"));
				address.setCountry(myRslt.getString("country"));
				address.setPhone(myRslt.getString("phone"));
				address.setPostalCode(myRslt.getString("postal_code"));
				allAddress.add(address);
			}
			closeConnection();
			return allAddress;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;

	}


	public Vector<String> getAllCountry() throws SQLException{
		Vector<String> allCountry = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select country from country");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allCountry.add(myRslt.getString("country"));
			}
			closeConnection();
			return allCountry;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;

	}

	public Vector<String> getAllCategory() throws SQLException{
		Vector<String> categories = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select name from category");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				categories.add(myRslt.getString("name"));
			}
			closeConnection();
			return categories;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;
	}
	
	
	public Vector<String> getAllLanguages() throws SQLException{
		Vector<String> languages = new Vector<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select name from language");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				languages.add(myRslt.getString("name"));
			}
			closeConnection();
			return languages;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;
	}
	
	
	public Vector<String> getAllDistrictInCountry(String countryName) throws SQLException{
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
			closeConnection();
			return allDistrict;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;

	}

	public Vector<String> getAllCitiesInDistrict(String districtName) throws SQLException{
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
			closeConnection();
			return allCities;
		}catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return null;

	}

	public int getCityIdByNameAndCountry(String city,String country) throws SQLException {
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
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return cityID;
	}


	public boolean insertCustomer(Customer customer) throws SQLException {
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
		catch(SQLException e1)
		{
			myConn.rollback();
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			myConn.rollback();
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return false;

	}
	
	public int insertActor(Actor actor) throws SQLException {
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
		catch(SQLException e1)
		{
			System.out.println("SQL Exeption, message is: " + e1.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return returnID;

	}
	
	public boolean insertFilmWithActors(Film film, ArrayList<Actor> actors) throws SQLException {
		boolean returnValue=false;
		try {
			int langID=getLanguageIdByName(film.getLanguage());
			int catID=getCategoryIdByName(film.getCategory());
			int filmID=-1;
			getConnection();	
			myConn.setAutoCommit(false);
			myStmt.executeUpdate("INSERT INTO film (title, description,release_year,language_id,rental_duration,length,special_features) "
					+ "VALUES ('"+film.getTitle()+"','" +film.getDescription()+"'," +film.getRelease_year()+"," +langID+
					"," +film.getRental_duration()+"," +film.getLength()+",'" +film.getSpecial_features()+"')" , Statement.RETURN_GENERATED_KEYS);
			myRslt = myStmt.getGeneratedKeys();
			if(myRslt.next()) filmID = myRslt.getInt(1);
			myStmt.executeUpdate("INSERT INTO inventory (film_id, store_id) "
					+ "VALUES ("+filmID+",1)");
			myStmt.executeUpdate("INSERT INTO film_category (film_id, category_id) "
					+ "VALUES ("+filmID+","+catID+")");
			for(Actor actor:actors) {
				int actorId=-1;
				myRslt = myStmt.executeQuery("SELECT * FROM actor where first_name = '"+actor.getFirstName()+"' and last_name = '" +actor.getLastName()+"'");
				if(myRslt.next()) {
					actorId = myRslt.getInt("actor_id");
				}
				else {
					myStmt.executeUpdate("INSERT INTO actor (first_name, last_name) "
							+ "VALUES ('"+actor.getFirstName()+"','" +actor.getLastName()+"')", Statement.RETURN_GENERATED_KEYS);
					myRslt = myStmt.getGeneratedKeys();
					if(myRslt.next()) actorId = myRslt.getInt(1);
				}				
				myStmt.executeUpdate("INSERT INTO film_actor (actor_id, film_id) "
						+ "VALUES ('"+actorId+"','" +filmID+"')");				
			}
			myConn.commit();
			returnValue=true;
		}
		catch(Exception ex)
		{
			myConn.rollback();
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return returnValue;

	}
	
	
	public int getLanguageIdByName(String language) throws SQLException {
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
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return returnID;
	}
	
	public int getCategoryIdByName(String name) throws SQLException {
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
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return returnID;
	}
	
}
//end class