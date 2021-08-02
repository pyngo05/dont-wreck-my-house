package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.DataException;
import learn.dontwreckmyhouse.data.GuestRepositoryDouble;
import learn.dontwreckmyhouse.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindByExistingEmail() {
        List<Guest> guests = service.findByEmail("cocomochi@gmail.com");
        assertEquals(1, guests.size());
    }

    @Test
    void shouldNotFindByMissingEmail() {
        List<Guest> guests = service.findByEmail("cocomochi1234@gmail.com");
        assertEquals(0, guests.size());
    }

    @Test
    void shouldFindByExistingGuestId() {
        List<Guest> guests = service.findByGuestId(1004);
        assertEquals(1, guests.size());
    }

    @Test
    void shouldNotFindByMissingGuestId() {
        List<Guest> guests = service.findByGuestId(1005);
        assertEquals(0, guests.size());
    }

}

