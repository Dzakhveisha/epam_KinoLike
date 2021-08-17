package com.epam.jwd.web.service;

import com.epam.jwd.web.dao.DaoImpl.JdbcCountryDao;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Country;

import java.util.List;

public class CountryService {
    private static final String NOT_FOUND_WITH_NAME_MSG = "Country with such name does not found!";
    private static final String NOT_FOUND_WITH_ID_MSG = "Country with such id does not found!";

    private static CountryService instance;
    private final JdbcCountryDao countryDao;

    private CountryService() {
        countryDao = JdbcCountryDao.getInstance();
    }

    public static CountryService getInstance() {
        CountryService localInstance = instance;
        if (localInstance == null) {
            synchronized (CountryService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new CountryService();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public List<Country> findAll() {
        return countryDao.findAll();
    }

    public Country findByName(String name) throws UnknownEntityException {
        return countryDao.findCountryByName(name).orElseThrow(() -> new UnknownEntityException(NOT_FOUND_WITH_NAME_MSG));
    }

    public Country findById(Long id) throws UnknownEntityException {
        return countryDao.findById(id).orElseThrow(() -> new UnknownEntityException(NOT_FOUND_WITH_ID_MSG));
    }

    public Country create(Country country) {
        return countryDao.save(country);
    }
}
