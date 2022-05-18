package helpers;


import java.sql.*;

/**
 * <b>DatabaseHandler</b> is a <i>helper</i> class for connecting, modifying and querying databases by utilizing <i>JDBC</i> and <i>SQLite</i>.
 */
public class DatabaseHandler {

    private Connection connection;
    private Statement statement;
    private static final String URL = "jdbc:sqlite:";

    /**
     * constructs new database handler and connects to <i>.db</i> file
     * @param dbFile String
     */
    public DatabaseHandler(String dbFile) {
        connection = null;
        connect(dbFile);
    }


    /**
     * loads SQL driver and connects database
     * @param dbFile String
     */
    private void connect(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            ConsoleLogger.printLog("SQL driver loaded");
            try {
                connection = DriverManager.getConnection(URL + dbFile);
                ConsoleLogger.printLog("Database connected");
            } catch (SQLException e) {ConsoleLogger.printLog("Database connection failed");}
        } catch (ClassNotFoundException e) {ConsoleLogger.printLog("Failed to load SQL driver");}
    }

    /**
     * commits asynchronous statement to database
     * @param modifySTMT String
     */
    public void modifyDB(String modifySTMT) {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ConsoleLogger.printLog("Statement created:\t" + modifySTMT);
            try {
                stmt.execute(modifySTMT);
                ConsoleLogger.printLog("Statement executed");
                try {
                    stmt.close();
                } catch (SQLException e) {ConsoleLogger.printLog("Statement could not be closed");}
            } catch (SQLException e) {ConsoleLogger.printLog("Statement execution failed");}
        } catch (SQLException e) {ConsoleLogger.printLog("Statement creation failed");}

    }

    /**
     * commits synchronous query statement to database and returns result set
     * @param querySTMT String
     * @return ResultSet
     * @throws NullPointerException
     */
    public ResultSet queryDB(String querySTMT) throws NullPointerException{

        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            ConsoleLogger.printLog("Statement created:\t" + querySTMT);
            try {
                 resultSet = stmt.executeQuery(querySTMT);
                 ConsoleLogger.printLog("Query executed");
            } catch (SQLException e) {ConsoleLogger.printLog("Query failed");}
        } catch (SQLException e) {ConsoleLogger.printLog("Statement creation failed");}
        finally {return resultSet;}

    }

}
