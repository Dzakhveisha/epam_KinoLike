package com.epam.jwd.web.model;

public class Movie implements DbEntity {
    private long id;
    private final String name;
    private final int year;
    private final String description;
    private final long country;
    private final Float rating;
    private final FilmGenre genre;

    public Movie(long id,String name, int year, String description, long countryId, float rating, int genreInt) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.country = countryId;
        this.rating = rating;
        this.genre = FilmGenre.getGenreById(genreInt);
    }

    public Movie(String name, Integer year, String description, long countryId, Long genreInt) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.country = countryId;
        this.rating = (float) 0;
        this.genre = FilmGenre.getGenreById(genreInt);
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public Long getCountry() {
        return country;
    }

    public float getRating() {
        return rating;
    }

    public FilmGenre getGenre() {
        return genre;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", rating=" + rating +
                ", genre=" + genre +
                '}';
    }
}
