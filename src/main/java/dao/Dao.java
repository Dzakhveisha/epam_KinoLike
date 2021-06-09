package dao;

import model.DbEntity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends DbEntity>{

    T save(T entity);

    List<T> findAll();

    Optional<T> findById(Long id);

    T update(T entity);

    void delete(Long id);

}