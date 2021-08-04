package carsharing.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_BASE_URL = "jdbc:h2:file:";
    private final String filepath;

    public Database(String fullPath) {
        this.filepath = fullPath;
    }

    public void initialize() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.h2.Driver");

            conn = DriverManager.getConnection(DB_BASE_URL + filepath);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE  COMPANY " +
                    "(ID INTEGER not NULL, " +
                    "NAME VARCHAR(255), " +
                    "PRIMARY KEY ( ID ))";

            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
