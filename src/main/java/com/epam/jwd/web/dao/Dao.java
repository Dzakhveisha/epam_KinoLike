package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.DbEntity;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface connect with database, do CRUD actions
 * @param <T> type of entity
 */
public interface Dao<T extends DbEntity>{

    /**
     * save entity to database
     *
     * @param entity to be saved to database
     * @return saved entity from database
     */
    T save(T entity);

    /**
     * find and return all entities in database
     * @return all entities
     */
    List<T> findAll();

    /**
     * find all entities ordered by sorting type in database
     *
     * @param sortType string specifies the sorting type
     * @return ordered entities
     */
    List<T> findAllSort(String sortType);

    /**
     * find entity by id in database
     *
     * @param id id of entity to be find
     * @return found entity
     */
    Optional<T> findById(Long id);

    /**
     * find and update entity with new values in database
     *
     * @param entity entity with new values
     * @return updated entity from database
     */
    T update(T entity);

    /**
     * delete entity with id in database
     *
     * @param id id of entity to be delete
     */
    void delete(Long id);

}
