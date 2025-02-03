import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGuruTest {
    
    // Database connection details (for testing purposes)
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username to search: ");
        String username = scanner.nextLine();

        // Security Issue: SQL Injection Vulnerability
        List<String> users = getUserData(username);

        // Inefficient Loop
        for (int i = 0; i < users.size(); i++) {
            System.out.println("User: " + users.get(i));
        }

        scanner.close();
    }

    public static List<String> getUserData(String username) {
        List<String> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Unoptimized Query + SQL Injection Risk
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                users.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}