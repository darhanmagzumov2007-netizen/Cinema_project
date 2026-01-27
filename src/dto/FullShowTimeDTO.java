package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FullShowTimeDTO {
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

    public FullShowtimeDTO() {}

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

}
