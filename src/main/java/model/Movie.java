package model;

public class Movie implements DbEntity {
    private final long id;
    private final String name;
    private final int year;
    private final String description;
    private final String country;
    private final float rating;
    private final FilmGenre genre;

    public Movie(long id,String name, int year, String description, String country, float rating, int genreInt) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.country = country;
        this.rating = rating;
        this.genre = FilmGenre.getRoleById(genreInt);
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

    public String getCountry() {
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
