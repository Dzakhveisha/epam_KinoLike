package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.DaoImpl.JdbcMovieDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmServiceTest {

    private static final String NOT_FOUND_WITH_NAME_MSG = "Film with such name does not exist!";
    private static final String NOT_FOUND_WITH_ID_MSG = "Film with such id does not exist!";
    private static JdbcMovieDao movieDao;

    @BeforeAll
    public static void setUp(){
        movieDao = mock(JdbcMovieDao.class);
    }

    @Test
    public void findByNotExistName() {
        String movieNotExistName = "filmName";
        when(movieDao.findMovieByName(movieNotExistName)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(UnknownEntityException.class,
                ()->{FilmService.getInstance().findByName(movieNotExistName);});
        assertEquals(NOT_FOUND_WITH_NAME_MSG, exception.getMessage());
    }

    @Test
    public void findByNotExistId() {
        long movieNotExistId = 178;
        when(movieDao.findById(movieNotExistId)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(UnknownEntityException.class,
                ()->{FilmService.getInstance().findById(movieNotExistId);});
        assertEquals(NOT_FOUND_WITH_ID_MSG, exception.getMessage());
    }
}