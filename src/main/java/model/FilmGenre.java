package model;

import exception.UnknownEntityException;

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

    public static FilmGenre getRoleById(long id){
        for (FilmGenre genre : values()) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        throw new UnknownEntityException("Impossible to find MovieGenre");
    }

    @Override
    public String toString() {
        return "FilmGenre{" +
                "value=" + this.name() +
                "id=" + id +
                '}';
    }
}
