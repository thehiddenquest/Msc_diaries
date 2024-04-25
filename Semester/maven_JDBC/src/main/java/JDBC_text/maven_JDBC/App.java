//package JDBC_text.maven_JDBC;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class App {
// 
//        // JDBC URL for MySQL
//        String jdbcUrl = "jdbc:mysql://localhost:3306/rahultest";
//
//        // Database credentials
//        String username = "root";
//        String password = "root";
//        TOA to = new TOA();
//     // SQL query
//        String sqlQuery = "SELECT * FROM testt";
//
//        // Establishing connection and executing the query
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
//        	 System.out.println("|ID|  |Name|  |gender|");
//            // Processing the query result
//            while (resultSet.next()) {
//                // Retrieve data from the result set
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String gender = resultSet.getString("gender");
//                to.setter(id, name, gender);
//                // Do something with the retrieved data (e.g., print it)
//                String recieve = to.getter();
//                System.out.println(recieve);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error executing the query!");
//            e.printStackTrace();
//        }}
//}
