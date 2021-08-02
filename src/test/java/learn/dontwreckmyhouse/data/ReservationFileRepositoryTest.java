package learn.dontwreckmyhouse.data;

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
        List<Reservation> actual = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertEquals(12, actual.size());
    }

    @Test
    void shouldFindByExistingReservationId() throws DataException {
        Reservation reservation = repository.findByReservationId(2, UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertEquals(BigDecimal.valueOf(1300), reservation.getTotal());
    }

    @Test
    void shouldAddValidReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setReservationId(13);
        reservation.setStartDate(LocalDate.ofEpochDay(2022 - 01 - 24));
        reservation.setEndDate(LocalDate.ofEpochDay(2022 - 01 - 28));
        reservation.setGuestId(15);
        reservation.setTotal(BigDecimal.valueOf(1400));

        reservation = repository.add(reservation);

        assertEquals(BigDecimal.valueOf(1400), reservation.getTotal());
    }

    @Test
    void shouldUpdateExisting() throws DataException {
        Reservation reservation = repository.findByReservationId(2, UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setGuestId(137);
        assertTrue(repository.update(reservation));
        assertNotNull(reservation);                        // confirm the reservation exists
        assertEquals(137, reservation.getGuestId());    // confirm the reservation was updated
    }

    @Test
    void shouldNotUpdateMissing() throws DataException {
        Reservation doesNotExist = new Reservation();
        doesNotExist.setReservationId(1024);
        assertFalse(repository.update(doesNotExist)); // can't update a reservation that doesn't exist
    }

    @Test
    void shouldDeleteByExistingId() throws DataException {
        List<Reservation> allReservations = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        int countBefore = allReservations.size();

        Reservation reservation = repository.findByReservationId(2, UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        boolean deleted = repository.delete(reservation);
        assertTrue(deleted);

        allReservations = repository.findByHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        int countAfter = allReservations.size();
        assertEquals(countBefore - 1, countAfter);
    }

    @Test
    void shouldNotReserveByOverlappingDateRange() {
        List<Reservation> reservations = repository.findByDateRange(startDate, endDate, UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldReserveByNonOverlappingDateRange() {
        List<Reservation> reservations = repository.findByDateRange(startDate2, endDate2, UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        assertEquals(0, reservations.size());
    }
}