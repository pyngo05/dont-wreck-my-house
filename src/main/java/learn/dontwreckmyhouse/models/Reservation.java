package learn.dontwreckmyhouse.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestId;
    private BigDecimal total;

    public int getId() {
        return id;
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

    public BigDecimal getTotal() {
        return total;
    }

}