package com.epam.jwd.web.model;

import com.epam.jwd.web.exception.UnknownEntityException;

public enum UserStatus implements DbEntity {
    LOOSER(1L),
    BEGINNER(2L),
    MIDDLE(3L),
    EXPERT(4L);

    private final long id;

    UserStatus(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserStatus getStatusById(long id) {
        for (UserStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Impossible to find UserStatus with such id.");
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "value=" + this.name() +
                "id=" + id +
                '}';
    }

    public static UserStatus getStatusByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            //todo log
            throw new UnknownEntityException("Impossible to find UserStatus with such name.");
        }
    }
}
