import exception.ConnectionPoolException;
import model.User;
import pool.ConcurConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, project !");
        ConcurConnectionPool pool = ConcurConnectionPool.getInstance();
        try {
            pool.init();
            Connection connection = pool.takeConnection();
            Statement stmt = connection.createStatement();

            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                final User user = new User(resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getInt("age"),
                        resultSet.getInt("roleId"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("statusId"));
                System.out.printf("user: %s\n", user);
            }
            stmt.close();
            pool.releaseConnection(connection);
            pool.destroy();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }
    }
}
