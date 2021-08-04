package learn.dontwreckmyhouse.data;


import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    final LocalDate date = LocalDate.of(2020, 6, 26);

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setHostId(UUID.fromString("4e52e660-3112-4d4f-9089-827819afa5de"));
        reservation.setGuestId(852);
        reservation.setStartDate(LocalDate.of(2022, 3, 16));
        reservation.setEndDate(LocalDate.of(2022, 3, 17));
        reservation.setTotal(BigDecimal.valueOf(374));
        reservations.add(reservation);

        reservation = new Reservation();
        reservation.setReservationId(2);
        reservation.setHostId(UUID.fromString("4e52e660-3112-4d4f-9089-827819afa5de"));
        reservation.setGuestId(211);
        reservation.setStartDate(LocalDate.of(2022, 4, 16));
        reservation.setEndDate(LocalDate.of(2022, 4, 21));
        reservation.setTotal(BigDecimal.valueOf(1234));
        reservations.add(reservation);

        reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setHostId(UUID.fromString("00000000-3112-4d4f-9089-827819afa5de"));
        reservation.setGuestId(123);
        reservation.setStartDate(LocalDate.of(2022, 1, 16));
        reservation.setEndDate(LocalDate.of(2022, 1, 21));
        reservation.setTotal(BigDecimal.valueOf(34));
        reservations.add(reservation);

//        TODO add more reservations as needed
    }

//    @Override
//    public List<Reservation> findByDateRange(LocalDate startDate, LocalDate endDate, UUID hostId) {
//        return null;
//    }

    @Override
    public Result<List<Reservation>> findByHostId(UUID hostId) {
        List<Reservation> res = reservations.stream()
                .filter(r -> r.getHostId().equals(hostId))
                .collect(Collectors.toList());
        return new Result<>(res);
    }

//    @Override
//    public Reservation findByReservationId(int reservationId, UUID hostId) throws DataException {
//        return null;
//    }
//
//    @Override
//    public Reservation add(Reservation reservation) throws DataException {
//        return null;
//    }
//
//    @Override
//    public boolean update(Reservation reservation) throws DataException {
//        return false;
//    }
//
//    @Override
//    public boolean delete(Reservation reservation) throws DataException {
//        return false;
//    }
}