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

import Model.*;
import javax.sql.*;
public class DbConnection
{

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;

	private DbConnection() {

	}

	private void getConnection() throws SQLException {
		//create a Connection object by calling a static method of DriverManager class
		myConn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/sakila?useSSL=false","root","password"
				);

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


	public List<String> getAllCountry() throws SQLException{
		List<String> allCountry = new ArrayList<String>();
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

	public List<String> getAllDistrictInCountry(String countryName) throws SQLException{
		List<String> allDistrict = new ArrayList<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct district from address inner join city "
					+ "on address.city_id=city.city_id inner join country "
					+ "on city.country_id=country.country_id and country = '"+countryName+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allDistrict.add(myRslt.getString("country"));
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

	public List<String> getAllCitiesInDistrict(String districtName) throws SQLException{
		List<String> allCities = new ArrayList<String>();
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct city from address inner join city "
					+ "on address.city_id=city.city_id and district = '"+districtName+"'");

			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				allCities.add(myRslt.getString("country"));
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

	public int getCityIdByName(String city) throws SQLException {
		try {
			getConnection();
			myRslt = myStmt.executeQuery("Select distinct city_id from city where city = '"+city+"'");		  
			//Step 4: PROCESS the myRslt result set object using a while loop
			myRslt.next();
			int cityID= myRslt.getInt("city_id");
			closeConnection();
			return cityID;
		}
		catch(Exception ex)
		{
			System.out.println("Some other Exception, message is: " + ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return -1;
	}


	public boolean insertCustomer(Customer customer) throws SQLException {

		try {
			Address address = customer.getAddress();
			int cityID = getCityIdByName(address.getCity());
			getConnection();
			myConn.setAutoCommit(false);
			int addressID = myStmt.executeUpdate("INSERT INTO address (address, district, city_id, postal_code, phone) "
					+ "VALUES ('"+address.getStreetAddress()+"','" +address.getDistrict()+"','" +cityID+"','" +address.getPostalCode()+"','" +address.getPhone()+"')");
			myStmt.executeUpdate("INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) "
					+ "VALUES (1,'"+customer.getFirstName()+"','" +customer.getLastName()+"','" +customer.getEmail()+"','" +addressID+"','" +customer.getActive()+"')");
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
	
	public boolean insertActor(Actor actor) throws SQLException {

		try {
			getConnection();			
			myStmt.executeUpdate("INSERT INTO actor (first_name, last_name) "
					+ "VALUES ('"+actor.getFirstName()+"','" +actor.getLastName()+"')");
			closeConnection();
			return true;
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
		return false;

	}
}
//end class