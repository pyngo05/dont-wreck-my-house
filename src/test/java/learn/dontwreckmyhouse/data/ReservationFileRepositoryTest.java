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
    void shouldAddValidReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHostId(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"));
        reservation.setReservationId(13);
        reservation.setStartDate(LocalDate.ofEpochDay(2022-01-24));
        reservation.setEndDate(LocalDate.ofEpochDay(2022-01-28));
        reservation.setGuest(15);
        reservation.setTotal(BigDecimal.valueOf(1400));

        reservation = repository.add(reservation);

        assertEquals(BigDecimal.valueOf(1400), reservation.getTotal());
    }

    @Test
    void update() throws DataException {
        Reservation reservation = repository.f(2);
        reservation.setGuestId(137);
        assertTrue(repository.update(reservation));

        reservation = repository.findById(2);
        assertNotNull(reservation);                        // confirm the memory exists
        assertEquals("Sherwin", reservation.getFrom());    // confirm the memory was updated
        assertFalse(reservation.isShareable());

        Reservation doesNotExist = new Reservation();
        doesNotExist.setId(1024);
        assertFalse(repository.update(doesNotExist)); // can't update a memory that doesn't exist
    }

    @Test
    void delete() {
    }

    @Test
    void serialize() {
    }

    @Test
    void deserialize() {
    }

    @Test
    void writeToFile() {
    }
}