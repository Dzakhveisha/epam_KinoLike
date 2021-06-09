import dao.impl.JdbcMovieDao;
import dao.impl.JdbcUserDao;
import exception.ConnectionPoolException;
import model.Movie;
import model.User;
import pool.ConcurrentConnectionPool;

public class Main {
    public static void main(String[] args) {
        ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
        try {
            pool.init();

            JdbcUserDao usersDao = new JdbcUserDao();
            usersDao.findAll().forEach(System.out::println);
            //usersDao.save(new User(4,"name",12, 2, "vbn@nm", "er", 2));
            JdbcMovieDao moviesDao = new JdbcMovieDao();
            moviesDao.findAll().forEach(System.out::println);
            //moviesDao.save(new Movie(2,"cvbn",2002,"ghjkl;lkjhgfdsdfghjkl", "США", 3,2));

            pool.destroy();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
