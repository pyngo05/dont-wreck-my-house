package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.models.Guest;

import java.util.List;

public interface GuestRepository {

    List<Guest> findAll();

    Guest findByGuestId(int guestId) throws DataException;

    List<Guest> findByEmail(String guestEmail);
}
