package model;

public class Country implements DbEntity {
    private final long id;
    private final String countryName;

    public Country(long id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
