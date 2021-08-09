package com.epam.jwd.web.dao.DaoImpl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.dao.CountryDao;
import com.epam.jwd.web.dao.SqlThrowingConsumer;
import com.epam.jwd.web.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcCountryDao extends BaseDao<Country> implements CountryDao {

    private static final String COUNTRY_TABLE_NAME = "country";
    private static final String COUNTRY_ID = "id";
    private static final String COUNTRY_NAME = "c_name";

    private static JdbcCountryDao instance;
    private final String findByNameSql;

    public static JdbcCountryDao getInstance() {
        JdbcCountryDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcCountryDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcCountryDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcCountryDao() {
        super(COUNTRY_TABLE_NAME);
        findByNameSql =  String.format(FIND_BY_PARAM_SQL_TEMPLATE, COUNTRY_TABLE_NAME, COUNTRY_NAME);;
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, Country entity) throws SQLException {
        statement.setString(1, entity.getCountryName());
        statement.setLong(2, entity.getId());
    }

    @Override
    protected Country mapResultSet(ResultSet resultSet) throws SQLException {
        return new Country(resultSet.getLong(COUNTRY_ID), resultSet.getString(COUNTRY_NAME));
    }

    @Override
    protected String getValuesForSaving(Country entity) {
        return String.format("(%d,'%s')", entity.getId(), entity.getCountryName());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s, %s", COUNTRY_ID, COUNTRY_NAME);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?", COUNTRY_NAME);
    }

    @Override
    public Optional<Country> findCountryByName(String name) {
        return takeFirstNotNull(findPreparedEntities(whereName(name), findByNameSql));
    }
    private static SqlThrowingConsumer<PreparedStatement> whereName(String name) {
        return statement -> {
            statement.setString(1, name);
        };
    }
}
