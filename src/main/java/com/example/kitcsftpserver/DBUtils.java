/**
 * @author Brendan Kite
 * Description: Class to
 */

// -- Package
package com.example.kitcsftpserver;

import java.sql.*;

// -- Class Declaration
public class DBUtils {

    public static void getLogin() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // -- Establishes the connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user-data", "root", "C00p3rH4s4Cut3Butt789");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE email = ?");
            String email = "";
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            String retrieveEmail = resultSet.getString("email");
            String retrievePassword = resultSet.getString("password");

        } catch (SQLException e) {
            e.printStackTrace();

            // -- Terminate the database connection
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
