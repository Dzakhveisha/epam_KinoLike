package dao;

import model.DbEntity;
import pool.ConcurrentConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseDao<T extends DbEntity> implements Dao<T> {

    protected static final String FIND_ALL_SQL_TEMPLATE = "select * from %s";
    protected static final String FIND_BY_PARAM_SQL_TEMPLATE = "select * from %s where %s = ?";
    private static final String SAVE_SQL_TEMPLATE = "INSERT %s(%s) VALUES";

    protected final String tableName;
    private final String findAllSql;
    private final String findByIdSql;
    private final String insertSql;

    public BaseDao(String tableName) {
        this.tableName = tableName;
        this.findAllSql = String.format(FIND_ALL_SQL_TEMPLATE, tableName);
        this.findByIdSql = String.format(FIND_BY_PARAM_SQL_TEMPLATE, tableName,"id");
        this.insertSql = String.format(SAVE_SQL_TEMPLATE, tableName, getFieldsNames());
    }

    protected abstract String getFieldsNames();

    private static SqlThrowingConsumer<PreparedStatement> whereId(long id) {
        return statement -> statement.setLong(1, id);
    }

    @Override
    public T save(T entity) {
        try {
            final ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
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
    public Optional<T> findById(Long id) {
        return takeFirstNotNull(
                findPreparedEntities(whereId(id), findByIdSql));
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    protected List<T> findPreparedEntities(SqlThrowingConsumer<PreparedStatement> consumer,String sql){
        List<T> entities = new ArrayList<>();
        try {
            final ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
            final Connection connection = pool.takeConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            consumer.accept(statement);
            try(final ResultSet resultSet = statement.executeQuery()) {
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
            final ConcurrentConnectionPool pool = ConcurrentConnectionPool.getInstance();
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

    protected Optional<T> takeFirstNotNull(List<T> entities){
        return entities.stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getValuesForSaving(T entity);
}
