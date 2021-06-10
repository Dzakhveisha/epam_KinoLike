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
            //usersDao.update(new User(8,"Vano",20,2, "США@mail", "3",2));

            JdbcMovieDao moviesDao = new JdbcMovieDao();
            moviesDao.findAll().forEach(System.out::println);
            moviesDao.update(new Movie(5,"cvbn",2002,"lol", 1, 3,2));
            //moviesDao.delete(3L);

            pool.destroy();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
