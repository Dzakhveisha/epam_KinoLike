package com.epam.jwd.web.dao.impl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.dao.ReviewDao;
import com.epam.jwd.web.dao.SqlThrowingConsumer;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class JdbcReviewDao extends BaseDao<Review> implements ReviewDao {

    private static final String REVIEW_TABLE_NAME = "review";
    private static final String REVIEW_USER_ID = "userId";
    private static final String REVIEW_FILM_ID = "filmId";
    private static final String REVIEW_VALUE = "value";
    private static final String REVIEW_TEXT = "text";

    private static JdbcReviewDao instance;
    private final String findByFilmAndUserSql;
    private final String findByUserSql;

    public static JdbcReviewDao getInstance() {
        JdbcReviewDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcReviewDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcReviewDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcReviewDao() {
        super(REVIEW_TABLE_NAME);
        this.findByFilmAndUserSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, REVIEW_TABLE_NAME, REVIEW_FILM_ID)
                + String.format(" AND %s = ?", REVIEW_USER_ID);
        this.findByUserSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, REVIEW_TABLE_NAME, REVIEW_USER_ID);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, Review entity) throws SQLException {

    }

    @Override
    protected Review mapResultSet(ResultSet resultSet) throws SQLException {
        return new Review(resultSet.getLong(REVIEW_USER_ID),
                resultSet.getLong(REVIEW_FILM_ID),
                resultSet.getInt(REVIEW_VALUE),
                resultSet.getString(REVIEW_TEXT));
    }

    @Override
    protected String getValuesForSaving(Review entity) {
        return String.format("(%d, %d, %d, '%s')",
                entity.getFilmId(), entity.getUserId(), entity.getValue(), entity.getText());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s, %s, %s, %s",
                REVIEW_FILM_ID, REVIEW_USER_ID, REVIEW_VALUE, REVIEW_TEXT);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?, %s = ?, %s = ?, %s = ? ",
                REVIEW_FILM_ID, REVIEW_USER_ID, REVIEW_VALUE, REVIEW_TEXT);
    }

    @Override
    public Optional<Review> findByFilmAndUser(Movie movie, User user) {
        return takeFirstNotNull(
                findPreparedEntities(whereUserAndFilm(movie.getId(), user.getId()), findByFilmAndUserSql));
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return findPreparedEntities(whereUser(user.getId()), findByUserSql);
    }

    private SqlThrowingConsumer<PreparedStatement> whereUser(Long userId) {
        return statement -> {
            statement.setString(1, userId.toString());
        };
    }

    private static SqlThrowingConsumer<PreparedStatement> whereUserAndFilm(Long filmId, Long userId) {
        return statement -> {
            statement.setString(1, filmId.toString());
            statement.setString(2, userId.toString());
        };
    }
}
