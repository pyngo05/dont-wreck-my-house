//package learn.dontwreckmyhouse.data;
//
//
//import learn.dontwreckmyhouse.models.Reservation;
//import learn.dontwreckmyhouse.models.Guest;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class ReservationRepositoryDouble implements ReservationRepository {
//
//    final LocalDate date = LocalDate.of(2020, 6, 26);
//
//    private final ArrayList<Reservation> reservations = new ArrayList<>();
//
//    public ReservationRepositoryDouble() {
//        Reservation reservation = new Reservation();
//        reservation.setId("498604db-b6d6-4599-a503-3d8190fda823");
//        reservation.setDate(date);
//        reservation.setForager(ForagerRepositoryDouble.FORAGER);
//        reservation.setItem(ItemRepositoryDouble.ITEM);
//        reservation.setKilograms(1.25);
//        reservation.add(reservation);
//    }
//
//    @Override
//    public List<Reservation> findByDateRange(LocalDate startDate, LocalDate endDate, UUID hostId) {
//        return null;
//    }
//
//    @Override
//    public List<Reservation> findByHostId(UUID hostId) {
//        return null;
//    }
//
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
//}