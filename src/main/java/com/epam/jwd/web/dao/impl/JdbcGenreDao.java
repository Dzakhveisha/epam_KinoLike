package com.epam.jwd.web.dao.impl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.model.FilmGenre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcGenreDao extends BaseDao<FilmGenre> {

    private static final String GENRE_TABLE_NAME = "genre";
    private static final String GENRE_ID = "id";
    private static final String GENRE_NAME = "g_name";

    public static JdbcGenreDao instance;

    public static JdbcGenreDao getInstance() {
        JdbcGenreDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcGenreDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcGenreDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcGenreDao() {
        super(GENRE_TABLE_NAME);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, FilmGenre entity) throws SQLException {

    }

    @Override
    protected FilmGenre mapResultSet(ResultSet resultSet) throws SQLException {
        return FilmGenre.getGenreById(resultSet.getLong(GENRE_ID));
    }

    @Override
    protected String getValuesForSaving(FilmGenre entity) {
        return String.format("('%s')", entity.name());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s", GENRE_NAME);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?", GENRE_NAME);
    }
}
