package GUI;
/**
 * Program Name: Driver1.java
 * Purpose: shows the steps involved in making a connection to a back end database
 *          using a JDBC driver and classes from the java.sql package. 
 * Coder: Bill Pulling 
 * Date: Jul 9, 2019
 */

//the DYNAMIC DUO
import java.sql.*;
import javax.sql.*;
public class Driver1
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
					"jdbc:mysql://localhost:3306/demo?useSSL=false","root","password"
					);
			
			//Step 2: create a Statement object by calling a method of the Connection object
			myStmt = myConn.createStatement();
			
			//Step 3: pass in a query string to the Statement object using a method
			// called executeQuery().
			//Assign the returned ResultSet object to myRslt.
			myRslt = myStmt.executeQuery("SELECT * FROM Employees");
			
			//Step 4: PROCESS the myRslt result set object using a while loop
			while(myRslt.next())
			{
				System.out.println(myRslt.getString("Last_Name") +
						               ", " + myRslt.getString("first_name") +
						               ", " + myRslt.getString("email") 
						               );//closing bracket for arg list of method println
			}
			
					
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