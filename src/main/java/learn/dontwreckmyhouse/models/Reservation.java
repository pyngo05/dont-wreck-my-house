package learn.dontwreckmyhouse.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.*;

public class Reservation {

    private int reservationId;
    private UUID hostId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestId;
    private BigDecimal total;

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
//        Host host = new Host();
//        List<Integer> days = new ArrayList<>();
//        int i = 0;
//        int j = 1;
//        while (i < daysBetween) {
//            days.set(i, startDate.getDayOfWeek().getValue());
//            i++;
//            days.set(j, )
//        }
//        daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//        //weekday rate
//        if (dayOfWeek != 6 && dayOfWeek != 7) {
//            total = host.getStandardRate().multiply(BigDecimal.valueOf(daysBetween))  ;
//        } else {
//            //weekend rate
//            total = host.getWeekendRate().multiply(BigDecimal.valueOf(daysBetween));
//        }
       return total;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}