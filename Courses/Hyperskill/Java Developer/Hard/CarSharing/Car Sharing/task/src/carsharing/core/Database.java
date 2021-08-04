package carsharing.core;

import carsharing.core.model.Car;
import carsharing.core.model.Company;

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

            initializeCompanyTable(stmt);
            initializeCarTable(stmt);

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

    public void initializeCompanyTable(Statement stmt) throws SQLException {
        String preSql = "DROP TABLE IF EXISTS company";
        String sql = "CREATE TABLE company(" +
                "id IDENTITY NOT NULL PRIMARY KEY, " +
                "name VARCHAR(255) UNIQUE NOT NULL" +
                ")";

        stmt.executeUpdate(preSql);
        stmt.executeUpdate(sql);
    }

    public void initializeCarTable(Statement stmt) throws SQLException {
        String preSql = "DROP TABLE IF EXISTS car";
        String sql = "CREATE TABLE car(" +
                "id IDENTITY NOT NULL PRIMARY KEY, " +
                "name VARCHAR(255) UNIQUE NOT NULL, " +
                "company_id INT NOT NULL, " +
                "CONSTRAINT fk_company_id FOREIGN KEY (company_id) " +
                "REFERENCES company(id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE SET NULL " +
                ")";

        stmt.executeUpdate(preSql);
        stmt.executeUpdate(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    public void insertCar(Car car) {
        String sql = "INSERT INTO car (name, company_id) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_BASE_URL + filepath);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, car.getName());
            stmt.setInt(2, car.getCompanyId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllCarsOfCompany(Company company) {
        String sql = "SELECT id, name, company_id " +
                "FROM car WHERE company_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_BASE_URL + filepath);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, company.getId());
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Car> cars = new ArrayList<>();
                while (resultSet.next()) {
                    Car car = new Car.Builder()
                            .setId(resultSet.getInt(1))
                            .setName(resultSet.getString(2))
                            .setCompanyId(resultSet.getInt(3))
                            .build();
                    cars.add(car);
                }
                return cars;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();
    }

}
