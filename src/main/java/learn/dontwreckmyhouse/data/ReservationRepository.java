package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

//    List<Reservation> findByDateRange(LocalDate startDate, LocalDate endDate, UUID hostId);

    Result<List<Reservation>> findByHostId(UUID hostId);

//    Reservation findByReservationId(int reservationId, UUID hostId) throws DataException;
//
//    Reservation add(Reservation reservation) throws DataException;

    Result<Reservation> update(Reservation reservation) throws DataException;

//    boolean delete(Reservation reservation) throws DataException;

}
