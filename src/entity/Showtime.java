package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Showtime {
    private Integer id;
    private Integer movieId;
    private Integer hallId;
    private LocalDate showDate;
    private LocalTime showTime;
    private double price;

    public Showtime() {
    }

    public Showtime(Integer id, int movieId, int hallId, LocalDate showDate, LocalTime showTime, double price) {
        this.id = id;
        this.movieId = movieId;
        this.hallId = hallId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
    }


    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Showtime(Integer id, LocalTime showTime, double price, LocalDate showDate, Integer movieId, Integer hallId) {
        this.id = id;
        this.showTime = showTime;
        this.price = price;
        this.showDate = showDate;
        this.movieId = movieId;
        this.hallId = hallId;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", hallId=" + hallId +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", price=" + price +
                '}';
    }
}