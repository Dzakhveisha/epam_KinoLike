package model;

import exception.UnknownEntityException;

public enum FilmGenre {
    COMEDY(1),
    DRAMA(2),
    ROMANCE(3),
    ADVENTURE(4),
    CARTOON(5),
    DETECTIVE(6),
    TRILLER(7),
    HORROR(8);

    private final Integer id;

    FilmGenre(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static FilmGenre getRoleById(int id){
        for (FilmGenre genre : values()) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        throw new UnknownEntityException("Impossible to find MovieGenre");
    }
}
