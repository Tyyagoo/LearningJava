package banking.system;

import banking.exceptions.InvalidCredentialsException;
import banking.exceptions.NonExistentAccountException;
import banking.service.Account;
import org.sqlite.SQLiteDataSource;

import java.math.BigDecimal;
import java.sql.*;

class Database {

    private final String url;
    private final SQLiteDataSource dataSource = new SQLiteDataSource();
    
    Database(String url) {
        this.url = url;
        dataSource.setUrl(url);
    }

    void initialize() {
        try (Connection cxn = dataSource.getConnection()) {
            try (Statement stmt = cxn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS card (\n" +
                        "id INTEGER PRIMARY KEY,\n" +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0" +
                        ");");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void insertAccount(Account account) {
        String sql = "INSERT INTO card(number,pin,balance) VALUES(?,?,?)";
        try (Connection cxn = dataSource.getConnection()) {
            try (PreparedStatement stmt = cxn.prepareStatement(sql)) {
                stmt.setString(1, account.getNumber());
                stmt.setString(2, account.getCard().getPin());
                stmt.setInt(3, account.getBalance().intValue());
                stmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteAccount(String number) {
        String sql = "DELETE FROM card WHERE number = ?";
        try (Connection cxn = dataSource.getConnection();
             PreparedStatement stmt = cxn.prepareStatement(sql)) {

            stmt.setString(1, number);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteAccount(Account account) {
        deleteAccount(account.getNumber());
    }

    void updateBalance(String number, BigDecimal balance) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?";

        try (Connection cxn = dataSource.getConnection();
             PreparedStatement stmt = cxn.prepareStatement(sql)) {

            stmt.setInt(1, balance.intValue());
            stmt.setString(2, number);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateBalance(Account account) {
        updateBalance(account.getNumber(), account.getBalance());
    }

    Account getAccount(String number) throws InvalidCredentialsException, NonExistentAccountException {
        if (number.length() != Bank.numberLength) throw new NonExistentAccountException();

        String sql = "SELECT pin, balance "
                    + "FROM card WHERE number = ?";

        try (Connection cxn = dataSource.getConnection()) {
            try (PreparedStatement stmt  = cxn.prepareStatement(sql)) {
                stmt.setString(1, number);
                try (ResultSet result = stmt.executeQuery()) {
                    if (result.next()) {
                        String pin = result.getString("pin");
                        BigDecimal balance = new BigDecimal(result.getInt("balance"));
                        return new Account(number, pin, balance);
                    }
                    throw new InvalidCredentialsException();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
