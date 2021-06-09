package dao;

import model.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findUserByName(String name);

}
