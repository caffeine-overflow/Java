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
import javax.sql.*;
public class DbConnection
{

	public static void main(String[] args) throws SQLException
	{
		// standard JDBC BOILERPLATE code
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRslt = null;
		
	
		//Step 1: Use a try-catch to attempt the database connection
		try
		{
			//create a Connection object by calling a static method of DriverManager class
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sakila?useSSL=false","root","password"
					);
			
			//Step 2: create a Statement object by calling a method of the Connection object
			myStmt = myConn.createStatement();
			
			//Step 3: pass in a query string to the Statement object using a method
			// called executeQuery().
			//Assign the returned ResultSet object to myRslt.
			myRslt = myStmt.executeQuery("SELECT * FROM country");
			
			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				//REVISION HERE: changed from column labels to column index numbers
				// NOTE: COLUMN INDEXING in an SQL TABLE STARTS AT ONE, NOT ZERO
				// Corresponding column indexes are 2, 3, and 4.
				System.out.println(myRslt.getString(2) +
						               ", " + myRslt.getString(3) +
						               ", " + myRslt.getString(4) 
						               );//closing bracket for arg list of method println
			}//end while			
					
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught, message is " + ex.getMessage());
		}
		
		//DO THE finally block!
		finally
		{
			//put your clean up code here to close the objects. Standard practice is to
			//close them in REVERSE ORDER of creation
			if(myRslt != null)
				myRslt.close();
			if(myStmt != null)
				myStmt.close();
			if(myConn != null)
				myConn.close();			
		}
		
		System.out.println("**\nEND OF PROGRAM");		

	}//end main

}
//end class