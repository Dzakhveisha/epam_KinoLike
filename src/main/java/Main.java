import dao.impl.JdbcMovieDao;
import dao.impl.JdbcUserDao;
import exception.ConnectionPoolException;
import model.User;
import pool.ConcurrentConnectionPool;

public class Main {
    public static void main(String[] args) {
        ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
        try {
            pool.init();

            JdbcUserDao usersDao = new JdbcUserDao();
            usersDao.findAll().forEach(System.out::println);

            JdbcMovieDao moviesDao = new JdbcMovieDao();
            moviesDao.findAll().forEach(System.out::println);

            pool.destroy();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
