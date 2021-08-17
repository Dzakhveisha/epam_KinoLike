package com.epam.jwd.web.model;

import com.epam.jwd.web.exception.UnknownEntityException;

public enum FilmGenre implements DbEntity {
    COMEDY(1L),
    DRAMA(2L),
    ROMANCE(3L),
    ADVENTURE(4L),
    CARTOON(5L),
    DETECTIVE(6L),
    TRILLER(7L),
    HORROR(8L);

    private final Long id;

    FilmGenre(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static FilmGenre getGenreById(long id){
        for (FilmGenre genre : values()) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        throw new UnknownEntityException("Impossible to find MovieGenre with such id.");
    }

    public static FilmGenre getGenreByName(String name){
        try{
            return valueOf(name);
        }catch (IllegalArgumentException e){
            //todo log
            throw new UnknownEntityException("Impossible to find MovieGenre with such name.");
        }
    }

    @Override
    public String toString() {
        return "FilmGenre{" +
                "value=" + this.name() +
                "id=" + id +
                '}';
    }
}
