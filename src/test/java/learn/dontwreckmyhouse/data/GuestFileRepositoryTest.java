package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-test-data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test-data/guests-test.csv";
    static final String TEST_DIR_PATH = "./data/guests-test-data";

    GuestFileRepository repository = new GuestFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        GuestFileRepository repo = new GuestFileRepository("./data/guests-test-data/guests-seed.csv");
        Result<List<Guest>> result = repo.findAll();
        assertTrue(result.isSuccess());
        assertEquals(1000, result.getPayload().size());
    }

    @Test
    void findByExistingGuestId() throws DataException {
        GuestFileRepository repo = new GuestFileRepository("./data/guests-test-data/guests-seed.csv");
        Result<Guest> guest = repo.findByGuestId(5);
        assertNotNull(guest);
        assertEquals("Berta", guest.getPayload().getFirstName());
    }

    @Test
    void shouldNotFindByMissingGuestId() throws DataException {
        Result<Guest> guest = repository.findByGuestId(1002);
        assertNull(guest.getPayload());
    }

//    @Test
//    void shouldFindByExistingEmail() {
//        List<Guest> guest = repository.findByEmail("wisaqb@blogger.com");
//        assertNotNull(guest);
//    }

}