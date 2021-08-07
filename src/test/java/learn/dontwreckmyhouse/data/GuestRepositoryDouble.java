package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        Guest guest = new Guest();
        guest.setGuestId(21);
        guest.setFirstName("Arte");
        guest.setLastName("Overell");
        guest.setEmail("aoverellk@w3.org");
        guest.setPhone("04) 1895104");
        guest.setState("WV");
        guests.add(guest);
    }

    @Override
    public Result<List<Guest>> findAll() {
        return new Result<>(guests);
    }


    @Override
    public Result<Guest> findByGuestId(int guestId) {
        return null; // TODO implement for service tests
    }

}