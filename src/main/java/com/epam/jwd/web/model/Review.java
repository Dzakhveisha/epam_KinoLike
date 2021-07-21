package com.epam.jwd.web.model;

public class Review implements DbEntity {
    private final Long id;
    private final long userId;
    private final long filmId;
    private final Integer value;
    private final String text;

    public Review(Long id, long userId, long filmId, Integer value, String text) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.value = value;
        this.text = text;
    }

    @Override
    public Long getId() {
        return id;
    }
}
