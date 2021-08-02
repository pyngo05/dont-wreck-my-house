package learn.dontwreckmyhouse.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.time.*;

public class Reservation {

    private int reservationId;
    private UUID hostId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestId;
    private BigDecimal total;
    private Host host;
    private Guest guest;
    private LocalDate date;

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setHostId(UUID hostId) {
        this.hostId = hostId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int id) {
        this.reservationId = id;
    }

    public UUID getHostId() {
        return hostId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public static int getDayNumber(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }

    public BigDecimal getTotal() {
       return total;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Host getHost() {
        return host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setHost(Host host) {
        this.host = host;
    }

}