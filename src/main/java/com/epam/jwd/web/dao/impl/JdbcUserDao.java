package com.epam.jwd.web.dao.impl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.dao.SqlThrowingConsumer;
import com.epam.jwd.web.dao.UserDao;
import com.epam.jwd.web.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao extends BaseDao<User> implements UserDao {

    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_AGE = "age";
    private static final String USER_ROLE_ID = "roleId";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_STATUS_ID = "statusId";

    private final String findByNameSql;

    private static JdbcUserDao instance;

    public static JdbcUserDao getInstance() {
        JdbcUserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcUserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcUserDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcUserDao() {
        super(USER_TABLE_NAME);
        this.findByNameSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, USER_TABLE_NAME, USER_LOGIN);
    }

    @Override
    protected User mapResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong(USER_ID),
                resultSet.getString(USER_LOGIN),
                resultSet.getInt(USER_AGE),
                resultSet.getInt(USER_ROLE_ID),
                resultSet.getString(USER_EMAIL),
                resultSet.getString(USER_PASSWORD),
                resultSet.getInt(USER_STATUS_ID));
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s, %s, %s, %s, %s, %s",
                USER_LOGIN, USER_PASSWORD, USER_ROLE_ID, USER_STATUS_ID, USER_EMAIL, USER_AGE);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? ",
                USER_LOGIN, USER_PASSWORD, USER_ROLE_ID, USER_STATUS_ID, USER_EMAIL, USER_AGE);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getPasswordHash());
        statement.setLong(3, entity.getRole().getId());
        statement.setLong(4, entity.getStatus().getId());
        statement.setString(5, entity.getEmail());
        statement.setLong(6, entity.getAge());
        statement.setLong(7, entity.getId());
    }

    @Override
    protected String getValuesForSaving(User entity) {
        return String.format("('%s', '%s', %d, %d, '%s', %d)",
                entity.getName(), entity.getPasswordHash(),
                entity.getRole().getId(), entity.getStatus().getId(),
                entity.getEmail(), entity.getAge());
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return takeFirstNotNull(
                findPreparedEntities(whereName(name), findByNameSql));
    }

    private static SqlThrowingConsumer<PreparedStatement> whereName(String name) {
        return statement -> statement.setString(1, name);
    }
}
