package dao.impl;

import dao.BaseDao;
import dao.MovieDao;
import dao.SqlThrowingConsumer;
import model.FilmGenre;
import model.Movie;

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

    private final String findByNameSql;
    private final String findByGenreSql;

    public JdbcMovieDao() {
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
    protected Movie mapResultSet(ResultSet resultSet) throws SQLException {
        return new Movie(resultSet.getLong(MOVIE_ID),
                resultSet.getString(MOVIE_NAME),
                resultSet.getInt(MOVIE_YEAR),
                resultSet.getString(MOVIE_DESCRIPTION),
                resultSet.getLong(MOVIE_COUNTRY),
                resultSet.getFloat(MOVIE_RATING),
                resultSet.getInt(MOVIE_GENRE_ID));
    }

    @Override
    protected String getValuesForSaving(Movie entity) {
        return String.format("('%s', %d, '%s', '%s', %d, %d)", entity.getName(), entity.getYear(),
                entity.getDescription(), entity.getCountry(),
                0, entity.getGenre().getId());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s, %s, %s, %s, %s, %s",
                MOVIE_NAME, MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_COUNTRY, MOVIE_RATING, MOVIE_GENRE_ID);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ",
                MOVIE_NAME, MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_COUNTRY, MOVIE_RATING, MOVIE_GENRE_ID);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, Movie entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getYear());
        statement.setString(3,entity.getDescription());
        statement.setLong(4,entity.getCountry());
        statement.setFloat(5,entity.getRating());
        statement.setLong(6,entity.getGenre().getId());
        statement.setLong(7,entity.getId());
    }

    private static SqlThrowingConsumer<PreparedStatement> whereName(String name) {
        return statement -> statement.setString(1, name);
    }

    private static SqlThrowingConsumer<PreparedStatement> whereGenre(long genreId) {
        return statement -> statement.setLong(1, genreId);
    }
}
