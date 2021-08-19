package com.epam.jwd.web.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.web.dao.DaoImpl.JdbcUserDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static final String NOT_EXIST_LOGIN = "Q";
    private static final String NOT_FOUND_MSG = "User with such login does not exist!";
    private static JdbcUserDao userDao;
    private static List<User> allUsers;
    private static BCrypt.Hasher hasher;



    @BeforeAll
    public static void setUp() {
        hasher = BCrypt.withDefaults();
        userDao = mock(JdbcUserDao.class);
        allUsers = new ArrayList<>();
        allUsers.add(new User(1, "Dasha", 19, 2, "dasha@mail", "ghj222", 2));
        allUsers.add(new User(2, "Vlad", 23, 1, "VKot@mail", "u213", 3));
        allUsers.add(new User(3, "Vano", 16, 2, "lol@mail", "lol", 1));
    }

    @Test
    public void findAllTest() {
        when(userDao.findAll()).thenReturn(allUsers);
        List<User> actual = UserService.getInstance(userDao).findAll();
        assertEquals(allUsers, actual);
    }

    @Test
    public void create() {
    }

    @Test
    public void findByLoginTest() {
        int index = 2;
        when(userDao.findUserByName(allUsers.get(index).getName()))
                .thenReturn(java.util.Optional.ofNullable(allUsers.get(index)));
        User actual = UserService.getInstance(userDao).findByLogin(allUsers.get(index).getName());
        assertEquals(allUsers.get(index), actual);
    }

    @Test
    public void findByNotExistLoginTest() {
        when(userDao.findUserByName(NOT_EXIST_LOGIN)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(UnknownEntityException.class,
                ()->{UserService.getInstance(userDao).findByLogin(NOT_EXIST_LOGIN);});
        assertEquals(NOT_FOUND_MSG, exception.getMessage());
    }

    @Test
    public void canLogInTest() {
        User correctUser = allUsers.get(0);
        final String encryptPassword = hasher.hashToString(MIN_COST, correctUser.getPasswordHash().toCharArray());
        User savedUser = new User(correctUser.getName(), correctUser.getAge(), correctUser.getRole().getId(),
                correctUser.getEmail(), encryptPassword, correctUser.getStatus().getId());

        when(userDao.findUserByName(savedUser.getName())).thenReturn(Optional.of(savedUser));
        assertTrue(UserService.getInstance(userDao).canLogIn(correctUser));
    }

    @Test
    public void cuntLogInTest() {
        User notExistUser = new User(1, "Dasssha", 19, 2, "dasha@mail", "ghj222", 2);
        when(userDao.findUserByName(notExistUser.getName())).thenReturn(Optional.empty());
        assertFalse(UserService.getInstance(userDao).canLogIn(notExistUser));
    }

    @Test
    public void canRegisterTest() {
        User notExistUser = new User(1, "Dora", 19, 2, "dasha@mail", "ghj222", 2);
        when(userDao.findUserByName(notExistUser.getName())).thenReturn(Optional.empty());
        assertTrue(UserService.getInstance(userDao).canRegister(notExistUser));
    }

    @Test
    public void cuntRegisterTest() {
        User ExistUser = allUsers.get(0);
        when(userDao.findUserByName(ExistUser.getName())).thenReturn(Optional.ofNullable(allUsers.get(0)));
        assertFalse(UserService.getInstance(userDao).canRegister(ExistUser));
    }
}