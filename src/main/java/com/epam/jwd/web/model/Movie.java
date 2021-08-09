package com.epam.jwd.web.model;

import com.epam.jwd.web.service.CountryService;

public class Movie implements DbEntity {
    private long id;
    private final String name;
    private final int year;
    private final String description;
    private final long countryId;
    private final String countryName;
    private final Float rating;
    private final FilmGenre genre;
    private final String imagePath;

    public Movie(long id, String name, int year, String description, long countryId, float rating, long genreInt, String imagePath) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.countryId = countryId;
        this.rating = rating;
        this.genre = FilmGenre.getGenreById(genreInt);
        this.imagePath = imagePath;
        countryName = getCountryName(this.countryId);
    }

    public Movie(String name, Integer year, String description, long countryId, Long genreInt, String imagePath) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.countryId = countryId;
        this.imagePath = imagePath;
        this.rating = (float) 0;
        this.genre = FilmGenre.getGenreById(genreInt);
        countryName = getCountryName(this.countryId);
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
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

    public float getRating() {
        return rating;
    }

    public FilmGenre getGenre() {
        return genre;
    }

    private String getCountryName(long countryId) {
        return CountryService.getInstance().findById(countryId).getCountryName();
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
                ", country='" + countryId + '\'' +
                ", rating=" + rating +
                ", genre=" + genre +
                '}';
    }
}
