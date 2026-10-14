import java.sql.*;

public class Database {
    private final Statement statement;

    // Constructor: menghubungkan program ke MySQL XAMPP
    public Database() {
        try {
            // Port default XAMPP adalah 3306, user "root", dan password kosong ""
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_people", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk query SELECT (mengambil data)
    public ResultSet selectQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk query INSERT, UPDATE, dan DELETE (memanipulasi data)
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Getter statement jika sewaktu-waktu dibutuhkan
    public Statement getStatement() {
        return statement;
    }
}