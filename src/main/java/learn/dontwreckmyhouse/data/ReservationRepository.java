package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    Result<List<Reservation>> findByDateRange(LocalDate startDate, LocalDate endDate, UUID hostId);

    Result<List<Reservation>> findByHostId(UUID hostId);

    Result<Reservation> add(Reservation reservation) throws DataException;

    Result<Reservation> update(Reservation reservation) throws DataException;

    Result<Reservation> delete(Reservation reservation) throws DataException;

}
