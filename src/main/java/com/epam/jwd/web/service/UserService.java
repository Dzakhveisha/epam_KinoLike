package com.epam.jwd.web.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.web.dao.UserDao;
import com.epam.jwd.web.dao.impl.JdbcUserDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.User;

import java.util.List;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;

public class UserService {

    private static UserService instance;
    private static final String NOT_FOUND_MSG = "User with such login does not exist!";

    private final UserDao userDao;
    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifier;

    private UserService() {
        userDao = JdbcUserDao.getInstance();
        hasher = BCrypt.withDefaults();
        verifier = BCrypt.verifyer();
    }

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new UserService();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User create(User user){
        final String encryptPassword = hasher.hashToString(MIN_COST, user.getPasswordHash().toCharArray());
        return userDao.save(new User(user.getName(), user.getAge(), user.getRole().getId(),
                user.getEmail(), encryptPassword, user.getStatus().getId()));
    }

    public User findByLogin(String login){
        return userDao.findUserByName(login).orElseThrow(() -> new UnknownEntityException(NOT_FOUND_MSG));
    }

    public boolean canLogIn(User user){
        try {
            User fundedUser = this.findByLogin(user.getName());
            return verifier.verify(user.getPasswordHash().getBytes(),
                    fundedUser.getPasswordHash().getBytes()).verified;
        }catch(UnknownEntityException e){
            return false;
        }
    }

    public boolean canRegister(User user){
        try {
            this.findByLogin(user.getName());
            return false;
        }catch(UnknownEntityException e){
            return true;
        }
    }

}

