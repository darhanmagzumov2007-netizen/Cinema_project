package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FullTicketInfoDTO {
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean booked) {
        isBooked = booked;
    }

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

    public FullTicketInfoDTO(Integer ticketId, String seatNumber, String customerName, Boolean isBooked, Integer showtimeId, LocalDate showDate, LocalTime showTime, Double price, Integer movieId, String movieTitle, String movieGenre, String movieCategory, Integer movieDuration, Integer hallId, String hallName, Integer hallCapacity) {
        this.ticketId = ticketId;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.isBooked = isBooked;
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieCategory = movieCategory;
        this.movieDuration = movieDuration;
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallCapacity = hallCapacity;
    }

    private Integer ticketId;
    private String seatNumber;
    private String customerName;
    private Boolean isBooked;
    private Integer showtimeId;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double price;
    private Integer movieId;
    private String movieTitle;
    private String movieGenre;
    private String movieCategory;
    private Integer movieDuration;
    private Integer hallId;
    private String hallName;
    private Integer hallCapacity;
    public FullTicketInfoDTO(int ticketId, int showtimeId, int movieId, int duration, int hallId, int capacity, String seatNumber, String customerName, String title, String genre, String category, String hallName, boolean booked, LocalDate showDate, LocalTime showTime) {
        this.ticketId = ticketId;
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.movieDuration = duration;
        this.hallId = hallId;
        this.hallCapacity = capacity;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.movieTitle = title;
        this.movieGenre = genre;
        this.movieCategory = category;
        this.hallName = hallName;
        this.isBooked = booked;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "FullTicketInfoDTO{" +
                "ticketId=" + ticketId +
                ", seatNumber='" + seatNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", isBooked=" + isBooked +
                ", showtimeId=" + showtimeId +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", price=" + price +
                '}';
    }
}
