package carsharing.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            String preSql = "DROP TABLE IF EXISTS company";
            String sql = "CREATE TABLE company(" +
                    "id IDENTITY NOT NULL PRIMARY KEY, " +
                    "name VARCHAR(255) UNIQUE NOT NULL" +
                    ")";

            stmt.executeUpdate(preSql);
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

    public void insertCompany(Company company) {
        String sql = "INSERT INTO COMPANY (name) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(DB_BASE_URL + filepath);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, company.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        String sql = "SELECT id, name " +
                "FROM COMPANY";
        try (Connection conn = DriverManager.getConnection(DB_BASE_URL + filepath);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Company> companies = new ArrayList<>();
                while (resultSet.next()) {
                    Company company = new Company.Builder()
                            .setId(resultSet.getInt(1))
                            .setName(resultSet.getString(2))
                            .build();
                    companies.add(company);
                }
                return companies;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return List.of();
    }
}
