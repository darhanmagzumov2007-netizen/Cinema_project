package entity;

import java.time.LocalDate;

public class Movie {
    private Integer id;
    private String title;
    private Integer duration;
    private String genre;
    private Double rating;
    private LocalDate releaseData;


    public Movie() {
    }

    public Movie(Integer id, String title, String genre, Integer duration, Double rating, LocalDate releaseData) {


        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.releaseData = releaseData;

    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(LocalDate releaseData) {
        this.releaseData = releaseData;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title=" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", releaseDate=" + releaseData +
                '}';
    }


}
