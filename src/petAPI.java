
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Methods that look up pets in the Pet table of pets.db.
 * Each method opens its own connection, runs one query, and returns the results as a list of Strings.
 * Run CreateDB first so that pets.db exists.
 */
public class petAPI {

    /**
     * Return the names of all animals in the database, of every type
     * @return an ArrayList of Strings -- the names of all animals
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static ArrayList<String> allNames() throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        // Return all rows from Pet table
        String sqlQuery = "SELECT * FROM Pet";
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next())
        {
            result.add(rs.getString("name"));
        }
        if(connection != null) {
            connection.close();
        }
        return result;
    }

    /**
     * Return the names of all animals of a given type
     * @param type a String e.g. "Dog" or "Cat"
     * @return an ArrayList of Strings -- the names of all animals of that type
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static ArrayList<String> namesOfType(String type) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        // Return all rows of the given type from Pet table
        String sqlQuery = "SELECT * FROM Pet where type = '" + type + "'";
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next())
        {
            result.add(rs.getString("name"));
        }
        if(connection != null) {
            connection.close();
        }
        return result;
    }

    /**
     * Return the names of all animals of a given type and color (presumably fur color)
     * @param animalColor a String e.g. "Black" or "Tabby" -- must match the capitalization in the database
     * @param type a String e.g. "Dog" or "Cat"
     * @return an ArrayList of Strings -- the names of all animals of that type and color
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static ArrayList<String> namesOfColors(String animalColor, String type) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        // Return all rows of the given color and type from Pet table
        String sqlQuery = "SELECT * FROM Pet where color = '" + animalColor + "' and type = '" + type + "'";
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next())
        {
            result.add(rs.getString("name"));
        }
        if(connection != null) {
            connection.close();
        }
        return result;
    }

    /**
     * Return the names of all animals of a given type that either are or are not spayed/neutered
     * @param spayedNeutered "1" for spayed/neutered or "0" for not (the database stores true/false as 1/0)
     * @param type a String e.g. "Dog" or "Cat"
     * @return an ArrayList of Strings -- the names of all matching animals
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static ArrayList<String> spayedNeuteredByAnimal(String spayedNeutered, String type) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        // Return all rows of the given type and spayed/neutered status from Pet table
        String sqlQuery = "SELECT * FROM Pet where spayedNeutered = '" + spayedNeutered  + "' and type = '" + type+ "'";
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next())
        {
            result.add(rs.getString("name"));
        }
        if(connection != null) {
            connection.close();
        }
        return result;
    }

    
    /**
     * Return the names of all animals of a given type whose birthday is in the given month
     * @param month the month as a number, e.g. 1 for January, 12 for December
     * @param type a String e.g. "Dog" or "Cat"
     * @return an ArrayList of Strings -- each entry is a name followed by the birthday,
     *         sorted by day of the month
     * @throws SQLException if the database can't be opened or the query fails
     */
    public static ArrayList<String> namesWithBirthdayMonth(int month, String type) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:pets.db");
        Statement statement = connection.createStatement();

        // birthday is stored as YYYY-MM-DD, so strftime('%m', birthday) gives the month, e.g. "05"
        // String.format("%02d", month) turns 5 into "05" so they match
        String sqlQuery = "SELECT * FROM Pet where strftime('%m', birthday) = '" + String.format("%02d", month) + "' " +
                          "and type = '" + type + "' ORDER BY strftime('%d', birthday)";
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next())
        {
            result.add(rs.getString("name") + " (" + rs.getString("birthday") + ")");
        }
        if(connection != null) {
            connection.close();
        }
        return result;
    }


    }


