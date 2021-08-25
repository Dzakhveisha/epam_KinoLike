package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.Country;

import java.util.Optional;

/**
 * DAO interface connect with database,
 * do CRUD actions,
 * do specific for Country actions
 */
public interface CountryDao extends Dao<Country> {

    /**
     * find country by name in database
     *
     * @param name name of country to be find
     * @return found country
     */
    Optional<Country> findCountryByName(String name);
}
