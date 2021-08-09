package com.epam.jwd.web.dao.DaoImpl;

import com.epam.jwd.web.dao.BaseDao;
import com.epam.jwd.web.model.UserStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserStatusDao extends BaseDao<UserStatus> {
    private static final String STATUS_TABLE_NAME = "userstatus";
    private static final String STATUS_ID = "id";
    private static final String STATUS_NAME = "s_name";

    public static JdbcUserStatusDao instance;

    public static JdbcUserStatusDao getInstance() {
        JdbcUserStatusDao localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcUserStatusDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new JdbcUserStatusDao();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private JdbcUserStatusDao() {
        super(STATUS_TABLE_NAME);
    }

    @Override
    protected void prepareForUpdate(PreparedStatement statement, UserStatus entity) throws SQLException {
        statement.setString(1, entity.name());
        statement.setLong(2, entity.getId());
    }

    @Override
    protected UserStatus mapResultSet(ResultSet resultSet) throws SQLException {
        return UserStatus.getStatusById(resultSet.getLong(STATUS_ID));
    }

    @Override
    protected String getValuesForSaving(UserStatus entity) {
        return String.format("('%s')", entity.name());
    }

    @Override
    protected String getFieldsNames() {
        return String.format("%s", STATUS_NAME);
    }

    @Override
    protected String getFieldsNamesForUpdate() {
        return String.format("%s = ?", STATUS_NAME);
    }
}
