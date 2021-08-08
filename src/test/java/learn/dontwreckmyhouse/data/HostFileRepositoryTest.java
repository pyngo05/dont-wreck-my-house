package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/hosts-test-data/hosts-seed.csv";
    static final String TEST_PATH = "./data/hosts-test-data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> all = repository.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void findByExistingHostId() {
        HostFileRepository repo = new HostFileRepository("./data/hosts-test-data/hosts-seed.csv");
        Result<Host> host = repo.findByHostId(UUID.fromString("582de161-b2eb-4ea6-8f28-b2e4be5f5ca0"));
        assertNotNull(host);
        assertEquals("Such", host.getPayload().getLastName());
    }

}