package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FullShowtimeDTO {
    private Integer showtimeId;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double price;

    private Integer movieId;
    private String movieTitle;
    private String movieGenre;
    private String movieCategory;
    private Integer movieDuration;
    private Double movieRating;

    private Integer hallId;
    private String hallName;
    private Integer hallCapacity;

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public Integer getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(Integer movieDuration) {
        this.movieDuration = movieDuration;
    }

    public Double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Double movieRating) {
        this.movieRating = movieRating;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getHallCapacity() {
        return hallCapacity;
    }

    public void setHallCapacity(Integer hallCapacity) {
        this.hallCapacity = hallCapacity;
    }

    public FullShowtimeDTO(Integer showtimeId, LocalDate showDate, LocalTime showTime, Double price, Integer movieId, String movieTitle, String movieGenre, String movieCategory, Integer movieDuration, Double movieRating, Integer hallId, String hallName, Integer hallCapacity) {
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieCategory = movieCategory;
        this.movieDuration = movieDuration;
        this.movieRating = movieRating;
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallCapacity = hallCapacity;
    }

    @Override
    public String toString() {
        return "Fullshowtime{" +
                "showtimeId=" + showtimeId +
                ", movie='" + movieTitle + '\'' +
                ", genre='" + movieGenre + '\'' +
                ", category='" + movieCategory + '\'' +
                ", hall='" + hallName + '\'' +
                ", date=" + showDate +
                ", time=" + showTime +
                ", price=" + price +
                ", duration=" + movieDuration +
                "min, rating=" + movieRating +
                '}';

    }
}

