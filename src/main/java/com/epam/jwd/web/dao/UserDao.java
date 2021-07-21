package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findUserByName(String name);

}
