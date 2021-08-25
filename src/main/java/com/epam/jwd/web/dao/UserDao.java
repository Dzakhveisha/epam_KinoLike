package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.User;

import java.util.Optional;

/**
 * DAO interface connect with database,
 * do CRUD actions,
 * do specific for User actions
 */
public interface UserDao extends Dao<User> {

    /**
     * find user by name in database
     *
     * @param name name of user to be find
     * @return found user
     */
    Optional<User> findUserByName(String name);

}
