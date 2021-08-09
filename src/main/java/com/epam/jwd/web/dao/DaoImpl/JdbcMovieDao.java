package com.epam.jwd.web.dao.DaoImpl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.dao.MovieDao;
import com.epam.jwd.web.dao.SqlThrowingConsumer;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcMovieDao extends BaseDao<Movie> implements MovieDao {

    private static final String MOVIE_TABLE_NAME = "movie";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_NAME = "m_name";
    private static final String MOVIE_YEAR = "year";
    private static final String MOVIE_DESCRIPTION = "description";
    private static final String MOVIE_COUNTRY = "countryId";
    private static final String MOVIE_RATING = "rating";
    private static final String MOVIE_GENRE_ID = "genreId";
    private static final String MOVIE_IMG_PATH = "imagePath";
    
    private static final String ORDER_BY_YEAR = " order by year desc";
    private static final String ORDER_BY_RATING = " order by rating desc";

    private final String findByNameSql;
    private final String findByGenreSql;

    private static JdbcMovieDao instance;

    public static JdbcMovieDao getInstance() {
        JdbcMovieDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcMovieDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcMovieDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcMovieDao() {
        super(MOVIE_TABLE_NAME);
        this.findByNameSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, MOVIE_TABLE_NAME, MOVIE_NAME);
        this.findByGenreSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, MOVIE_TABLE_NAME, MOVIE_GENRE_ID);
    }

    @Override
    public Optional<Movie> findMovieByName(String name) {
        return takeFirstNotNull(
                findPreparedEntities(whereName(name), findByNameSql));
    }

    @Override
    public List<Movie> findMoviesByGenre(FilmGenre genre) {
        return findPreparedEntities(whereGenre(genre.getId()), findByGenreSql);
    }

    @Override
    public List<Movie> findAllByNew() {
        return this.findAllSort(ORDER_BY_YEAR);
    }

    @Override
    public List<Movie> findAllByPopular() {
        return this.findAllSort(ORDER_BY_RATING);
    }

    @Override
    protected Movie mapResultSet(ResultSet resultSet) throws SQLException {
        return new Movie(resultSet.getLong(MOVIE_ID),
                resultSet.getString(MOVIE_NAME),
                resultSet.getInt(MOVIE_YEAR),
                resultSet.getString(MOVIE_DESCRIPTION),
                resultSet.getLong(MOVIE_COUNTRY),
                resultSet.getFloat(MOVIE_RATING),
                resultSet.getInt(MOVIE_GENRE_ID),
                resultSet.getString(MOVIE_IMG_PATH));
    }

    @Override
    protected String getValuesForSaving(Movie entity) {
        return String.format("('%s', %d, '%s', '%s', %d, %d, '%s')", entity.getName(), entity.getYear(),
                entity.getDescription(), entity.getCountryId(), 0, entity.getGenre().getId(), entity.getImagePath());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s, %s, %s, %s, %s, %s, %s",
                MOVIE_NAME, MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_COUNTRY, MOVIE_RATING, MOVIE_GENRE_ID, MOVIE_IMG_PATH);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ",
                MOVIE_NAME, MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_COUNTRY, MOVIE_RATING, MOVIE_GENRE_ID, MOVIE_IMG_PATH);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, Movie entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getYear());
        statement.setString(3, entity.getDescription());
        statement.setLong(4, entity.getCountryId());
        statement.setFloat(5, entity.getRating());
        statement.setLong(6, entity.getGenre().getId());
        statement.setString(7, entity.getImagePath());
        statement.setLong(8, entity.getId());
    }

    private static SqlThrowingConsumer<PreparedStatement> whereName(String name) {
        return statement -> statement.setString(1, name);
    }

    private static SqlThrowingConsumer<PreparedStatement> whereGenre(long genreId) {
        return statement -> statement.setLong(1, genreId);
    }
}
