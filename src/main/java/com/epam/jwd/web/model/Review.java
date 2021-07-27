package com.epam.jwd.web.model;

public class Review implements DbEntity {
    private final long userId;
    private final long filmId;
    private final Integer value;
    private final String text;

    public Review( long userId, long filmId, Integer value, String text) {
        this.userId = userId;
        this.filmId = filmId;
        this.value = value;
        this.text = text;
    }

    public long getUserId() {
        return userId;
    }

    public long getFilmId() {
        return filmId;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public Long getId() {
        return (long)hashCode();
    }
}
