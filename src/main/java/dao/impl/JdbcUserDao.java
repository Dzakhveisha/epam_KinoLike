package dao.impl;

import dao.BaseDao;
import dao.UserDao;
import model.User;
import dao.SqlThrowingConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao extends BaseDao<User> implements UserDao {

    private static final String USER_TABLE_NAME = "users";
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_AGE = "age";
    private static final String USER_ROLE_ID = "roleId";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_STATUS_ID = "statusId";

    private final String findByNameSql;

    public JdbcUserDao() {
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
    public Optional<User> findUserByName(String name) {
        return  takeFirstNotNull(
                findPreparedEntities(whereName(name), findByNameSql));
    }

    private static SqlThrowingConsumer<PreparedStatement> whereName(String name) {
        return statement -> statement.setString(1, name);
    }
}
