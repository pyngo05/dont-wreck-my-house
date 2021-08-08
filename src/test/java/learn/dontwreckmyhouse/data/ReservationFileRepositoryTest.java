package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservations/reservation-test-data/reservations-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations/reservation-test-data/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    final LocalDate startDate = LocalDate.of(2022, 1, 5);
    final LocalDate endDate = LocalDate.of(2022, 1, 10);
    final LocalDate startDate2 = LocalDate.of(2023, 1, 5);
    final LocalDate endDate2 = LocalDate.of(2023, 1, 10);

    ReservationFileRepository repository = new ReservationFileRepository("./data/reservations/reservation-test-data");

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByExistingHostId() {
        Result<List<Reservation>> result = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertTrue(result.isSuccess());
        assertEquals(12, result.getPayload().size());
    }

    @Test
    void shouldNotFindByMissingHostId() {
        Result<List<Reservation>> result = repository.findByHostId(UUID.fromString("9e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertFalse(result.isSuccess()); // expectedly fails because file does not exist
    }

    @Test
    void shouldAddValidReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setReservationId(13);
        reservation.setStartDate(LocalDate.of(2022, 01, 24));
        reservation.setEndDate(LocalDate.of(2022, 01, 28));
        reservation.setGuestId(15);
        reservation.setTotal(BigDecimal.valueOf(1100));

        Result<Reservation> result = repository.add(reservation);
        assertTrue(result.isSuccess());
        assertEquals(BigDecimal.valueOf(1100), result.getPayload().getTotal());
    }

    @Test
    void shouldUpdateExisting() throws DataException {
        // do update
        Reservation reservation = new Reservation();
        reservation.setHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setReservationId(12);
        reservation.setStartDate(LocalDate.of(2032, 01, 05));
        reservation.setEndDate(LocalDate.of(2032, 01, 10));
        reservation.setGuestId(735);
        reservation.setTotal(BigDecimal.valueOf(1100));
        Result<Reservation> result = repository.update(reservation);
        assertTrue(result.isSuccess());

        // check if repository was updated as requested
        Result<List<Reservation>> findResult = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertTrue(findResult.isSuccess());
        List<Reservation> reservations = findResult.getPayload();

        // get reservation with id 12 to check it was updated
        Reservation updatedReservation = null;
        for (Reservation res : reservations) {
            if (res.getReservationId() == 12) {
                updatedReservation = res;
            }
        }
        assertEquals(12, updatedReservation.getReservationId());
        assertEquals(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"), updatedReservation.getHostId());
        assertEquals(LocalDate.of(2032, 01, 05), updatedReservation.getStartDate());
        assertEquals(LocalDate.of(2032, 01, 10), updatedReservation.getEndDate());
        assertEquals(735, updatedReservation.getGuestId());
        assertEquals(BigDecimal.valueOf(1100.0), updatedReservation.getTotal());
    }

    @Test
    void shouldDeleteByExistingId() throws DataException {
        Result<List<Reservation>> allReservations = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        int countBefore = allReservations.getPayload().size();
        List<Reservation> reservations = allReservations.getPayload();

        Reservation deleteReservation = null;
        for (Reservation res : reservations) {
            if (res.getReservationId() == 12) {
                deleteReservation = res;
            }
        }

        Result<Reservation> deleted = repository.delete(deleteReservation);
        assertTrue(deleted.isSuccess());

        allReservations = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        int countAfter = allReservations.getPayload().size();
        assertEquals(countBefore - 1, countAfter);
    }

    @Test
    void shouldNotReserveByOverlappingDateRange() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setReservationId(12);
        reservation.setStartDate(LocalDate.of(2021, 06, 28));
        reservation.setEndDate(LocalDate.of(2021, 06, 29));
        reservation.setGuestId(15);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<List<Reservation>> result = repository.findByDateRange
                (LocalDate.of(2021, 06, 28), LocalDate.of(2021, 06, 29), UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertTrue(result.isSuccess());
    }

}