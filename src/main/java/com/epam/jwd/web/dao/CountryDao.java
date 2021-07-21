package com.epam.jwd.web.dao;

import com.epam.jwd.web.model.Country;

import java.util.Optional;

public interface CountryDao extends Dao<Country> {

    Optional<Country> findCountryByName(String name);
}
