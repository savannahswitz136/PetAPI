import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Example of reading from pets.db: prints every pet in the Pet table,
 * then prints a list of just the cats' names.
 */
public class Example {

    /**
     * Print every pet's name, type, and id, then print the names of all the cats
     * @param args not used
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static void main(String[] args) throws SQLException 
    {
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        String sqlQuery = "SELECT * FROM Pet"; // Return all (*) rows from Pet table
        ResultSet rs = statement.executeQuery(sqlQuery);

        //list to hold the cats' names
        ArrayList<String> catNames = new ArrayList<String>();

        //loop through all of the results: print every pet, and save the cats' names
        while(rs.next())
        {
            System.out.print(rs.getString("name"));
            System.out.print(" " + rs.getString("type"));
            System.out.println(" id: " + rs.getInt("id"));

            if (rs.getString("type").equals("Cat"))
            {
                catNames.add(rs.getString("name"));
            }
        }
        connection.close();

        //print out all of the cats' names
        System.out.println("\nCats:");
        for (String name : catNames)
        {
            System.out.println(name);
        }
    }

}

