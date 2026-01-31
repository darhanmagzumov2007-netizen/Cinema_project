package entity;

public class Ticket {
    private Integer id;
    private Integer showtimeId;
    private String seatNumber;
    private String customerName;
    private Boolean isBooked;

    public Ticket() {
    }

    public Ticket(String customerName, Integer showtimeId, String seatNumber, Object seatNumber1, boolean isBooked) {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    public Ticket(String customerName, Integer showtimeId, Integer id, String seatNumber, Boolean isBooked) {
        this.customerName = customerName;
        this.showtimeId = showtimeId;
        this.id = id;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", showtimeId=" + showtimeId +
                ", seatNumber='" + seatNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", isBooked=" + isBooked +
                '}';
    }


    public boolean getIsBooked() {
        return Boolean.TRUE.equals(isBooked);
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}
