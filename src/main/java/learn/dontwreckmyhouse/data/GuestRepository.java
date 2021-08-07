package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;

import java.util.List;

public interface GuestRepository {

    Result<List<Guest>> findAll();

    Result<Guest> findByGuestId(int guestId);


//    List<Guest> findByEmail(String guestEmail);
}
