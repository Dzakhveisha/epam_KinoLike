package com.epam.jwd.web.model;

import com.epam.jwd.web.exception.UnknownEntityException;

import java.util.Arrays;
import java.util.List;

public enum UserRole implements DbEntity {
    ADMIN(1L),
    USER(2L),
    UNAUTHORIZED(3L);

    private static final List<UserRole> ALL_AVAILABLE_ROLES = Arrays.asList(UserRole.values());
    private final long id;

    UserRole(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserRole getRoleById(long id) throws UnknownEntityException {
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new UnknownEntityException("Impossible to find UserRole");
    }

    public static UserRole getRoleByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            //todo log
            throw new UnknownEntityException("Impossible to find UserRole");
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "value=" + this.name() +
                "id=" + this.id +
                '}';
    }

    public static List<UserRole> valuesAsList(){
        return ALL_AVAILABLE_ROLES;
    }
}
