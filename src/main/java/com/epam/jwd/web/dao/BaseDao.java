package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.DbEntity;
import com.epam.jwd.web.pool.ConcurrentConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseDao<T extends DbEntity> implements Dao<T> {

    private static final String FIND_ALL_SQL_TEMPLATE = "select * from %s";
    protected static final String FIND_BY_PARAM_SQL_TEMPLATE = "select * from %s where %s = ?";
    private static final String SAVE_SQL_TEMPLATE = "INSERT %s(%s) VALUES";
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s WHERE id = ?";
    private static final String UPDATE_SQL_TEMPLATE = "UPDATE %s SET %s WHERE id = ?";

    protected final ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
    protected final String tableName;
    private final String findAllSql;
    private final String findByIdSql;
    private final String insertSql;
    private final String deleteSql;
    private final String updateSql;

    public BaseDao(String tableName) {
        this.tableName = tableName;
        this.findAllSql = String.format(FIND_ALL_SQL_TEMPLATE, tableName);
        this.findByIdSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, tableName, "id");
        this.insertSql = String.format(SAVE_SQL_TEMPLATE, tableName, getFieldsNames());
        this.deleteSql = String.format(DELETE_SQL_TEMPLATE, tableName);
        this.updateSql = String.format(UPDATE_SQL_TEMPLATE, tableName, getFieldsNamesForUpdate());
    }

    @Override
    public T save(T entity) {
        try {
            final Connection connection = pool.takeConnection();
            Statement statement = connection.createStatement();
            String sql = insertSql + getValuesForSaving(entity);
            statement.executeUpdate(sql);
            statement.close();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public List<T> findAll() {
        return findEntities(findAllSql);
    }

    @Override
    public List<T> findAllSort(String orderType){
        return findEntities(findAllSql + orderType);
    }

    @Override
    public Optional<T> findById(Long id) {
        return takeFirstNotNull(
                findPreparedEntities(whereId(id), findByIdSql));
    }

    @Override
    public T update(T entity) {
        try {
            final Connection connection = pool.takeConnection();
            PreparedStatement statement = connection.prepareStatement(updateSql);
            prepareForUpdate(statement, entity);
            statement.executeUpdate();
            statement.close();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        try {
            final Connection connection = pool.takeConnection();
            PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<T> findPreparedEntities(SqlThrowingConsumer<PreparedStatement> consumer, String sql) {
        List<T> entities = new ArrayList<>();
        try {
            final Connection connection = pool.takeConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            consumer.accept(statement);
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final T entity = mapResultSet(resultSet);
                    entities.add(entity);
                }
            }
            statement.close();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }


    protected List<T> findEntities(String sql) {
        List<T> entities = new ArrayList<>();
        try {
            final Connection connection = pool.takeConnection();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                final T entity = mapResultSet(resultSet);
                entities.add(entity);
            }
            statement.close();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    protected Optional<T> takeFirstNotNull(List<T> entities) {
        return entities.stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    private static SqlThrowingConsumer<PreparedStatement> whereId(long id) {
        return statement -> statement.setLong(1, id);
    }

    protected abstract void prepareForUpdate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getValuesForSaving(T entity);

    protected abstract String getFieldsNames();

    protected abstract String getFieldsNamesForUpdate();
}
