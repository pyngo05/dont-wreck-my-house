package learn.dontwreckmyhouse.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Reservation {



    private int id;



    private UUID hostId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestId;
    private BigDecimal total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BigDecimal getTotal() {
        return total;
    }

}