package model;

public class Movie {
    private final int id;
    private final int year;
    private final String description;
    private final String country;
    private final float rating;
    private final FilmGenre genre;

    public Movie(int id, int year, String description, String country, float rating, int genreInt) {
        this.id = id;
        this.year = year;
        this.description = description;
        this.country = country;
        this.rating = rating;
        this.genre = FilmGenre.getRoleById(genreInt);
    }
}
