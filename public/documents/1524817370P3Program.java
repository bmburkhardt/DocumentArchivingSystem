// Bryan Burkhardt
// CS 3743 - Program 3
// 19 Apr 2018
// P3Program.java
// MySQL program demonstrating various ways to show/insert/etc data.

package cs3743;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class P3Program 
{
    private Connection connect = null;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public static final int ER_DUP_ENTRY = 1062;
    public static final int ER_DUP_ENTRY_WITH_KEY_NAME = 1586;
    public static final String[] strFlightIdM =
    {   "510"
       ,"705"
       ,"331"
       ,"END"
    };
    
    public P3Program (String user, String password) throws Exception
    {
        try
        {
            // This will load the MySQL driver, each DBMS has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            this.connect = DriverManager.getConnection
                    ("jdbc:mysql://10.100.1.81:3306/xmv643db"
                    , user
                    , password);
        }
        catch (Exception e) 
        {
            throw e;
        } 
        
    }
       
    public void runProgram() throws Exception 
    {
        try 
        {
            statement = connect.createStatement();

            // Part 3a
            resultSet = statement.executeQuery("select c.* from Customer c");

            // Part 3b
            printCustomers("Beginning Customers", resultSet);

            // Part 3c
            resultSet = statement.executeQuery("select f.* from Flight f");

            // Part 3d
            MySqlUtility.printUtility("Beginning Flights", resultSet);

            // Part 3e
            try
            {
                statement.executeUpdate("insert into Customer "
                        + "(`custNr`, `name`, `preferAirline`, `birthDt`, `gender`)"
                        + "values(1999, \"Bryan Burkhardt\", \"Alaska\", \"1989-12-25\", \"M\")");
            }
            catch (SQLException e)
            {
                switch (e.getErrorCode())
                {
                    case ER_DUP_ENTRY:
                    case ER_DUP_ENTRY_WITH_KEY_NAME:
                        System.out.printf("Duplicate key error: %s\n", e.getMessage());
                        break;
                    default:
                        throw e;
                }
            }

            // Part 3f
            resultSet = statement.executeQuery("select c.* from Customer c");
            printCustomers("Customers after I was added", resultSet);

            // Part 3g
            preparedStatement = connect.prepareStatement("insert into Reservation values (?, ?, ?)");
            
            // Part 3h
            for(int i = 0; i < strFlightIdM.length; ++i) {
                if(strFlightIdM[i] == "END") 
                {
                    break;
                }
                preparedStatement.setInt(1, 1999);
                preparedStatement.setString(2, strFlightIdM[i]);
                preparedStatement.setInt(3, 2);
                try 
                {
                    preparedStatement.executeUpdate();
                }
                catch (SQLException e)
                {
                    switch (e.getErrorCode())
                    {
                        case ER_DUP_ENTRY:
                        case ER_DUP_ENTRY_WITH_KEY_NAME:
                            System.out.printf("Duplicate key error: %s\n", e.getMessage());
                            break;
                        default:
                            throw e;
                    }
                }
            }

            // Part 3i
            preparedStatement = connect.prepareStatement("select * from Reservation where custNr = ?");
            preparedStatement.setInt(1, 1999);
            resultSet = preparedStatement.executeQuery();
            MySqlUtility.printUtility("My reservations", resultSet);

            // Part 3j
            resultSet = statement.executeQuery("select r.flightId, c.custNr, c.name from Customer c, "
                                             + "Reservation r, Reservation r1999, Flight f "
                                             + "where c.custNr != 1999 and r1999.custNr=1999 and "
                                             + "r1999.flightId = f.flightId and f.flightId = r.flightId "
                                             + "and r.custNr = c.custNr");
            MySqlUtility.printUtility("Other customers on my flights", resultSet);

            // Part 3k
            resultSet = statement.executeQuery("select r.flightId, COUNT(*) from Reservation r "
                                             + "group by r.flightId having COUNT(r.flightId) > 1");
            MySqlUtility.printUtility("Flights having more than 2 reservations", resultSet);

            // Part 3l
            statement.executeUpdate("delete from Reservation where custNr = 1999");

            // Part 3m
            resultSet = statement.executeQuery("select r.flightId, c.custNr, c.name from Customer c, "
                                             + "Reservation r, Reservation r1999, Flight f "
                                             + "where c.custNr != 1999 and r1999.custNr = 1999 and "
                                             + "r1999.flightId = f.flightId and f.flightId = r.flightId "
                                             + "and r.custNr = c.custNr");
            MySqlUtility.printUtility("Other customers on my flights after mine were deleted", resultSet);
        } 
        catch (Exception e) 
        {
            throw e;
        } 
        finally 
        {
            close();
        }

    }                                                                                                                        
    

    // printCustomers
    // Creates a neat print function to display data within the customer table. If a value
    //      in the table is null, it is represented with a "---"
    private void printCustomers(String title, ResultSet resultSet) throws SQLException 
    {
        System.out.printf("%s\n", title);
		System.out.printf("%3s %-10s %-15s %-18s %-11s %-6s\n", "", "Customer#", "Name", "Preferred Airline", "D.O.B", "Gender");


		while(resultSet.next())
		{
			String birthDtStr;

			int custNr = resultSet.getInt("custNr");
			String nameStr = resultSet.getString("name");
			if(nameStr == null)
				nameStr = "---";
			String preferAirlineStr = resultSet.getString("preferAirline");
			if(preferAirlineStr == null)
				preferAirlineStr = "---";
			java.sql.Date dob = resultSet.getDate("birthDt");
			if(dob == null)
				birthDtStr = "---";
			else
				birthDtStr = dob.toString();
			String genderStr = resultSet.getString("gender");
			if(genderStr == null)
				genderStr = "---";

			System.out.printf("%3s %-10s %-15s %-18s %-11s %-6s\n"
				, ""
		        , custNr
				, nameStr
				, preferAirlineStr
				, birthDtStr
				, genderStr);
		}
		System.out.printf("\n");
    }
    
    // Close the resultSet, statement, preparedStatement, and connect
    private void close() 
    {
        try 
        {
            if (resultSet != null) 
                resultSet.close();

            if (statement != null) 
                statement.close();
            
            if (preparedStatement != null) 
                preparedStatement.close();

            if (connect != null) 
                connect.close();
        } 
        catch (Exception e) 
        {

        }
    }

}
