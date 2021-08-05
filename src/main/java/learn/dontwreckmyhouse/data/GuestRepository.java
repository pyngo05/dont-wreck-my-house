package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;

import java.util.List;
import java.util.UUID;

public interface GuestRepository {

    Result<List<Guest>> findAll();

    Result<Guest> findByGuestId(int guestId);


//    List<Guest> findByEmail(String guestEmail);
}
