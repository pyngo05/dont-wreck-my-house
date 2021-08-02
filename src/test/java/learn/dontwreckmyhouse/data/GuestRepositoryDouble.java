package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.models.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository  {

    public final static Guest GUEST = makeGuest();

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findByGuestId(int guestId) throws DataException {
        return guests.stream()
                .filter(i -> i.getGuestId() == guestId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findByEmail(String guestEmail) {
        return guests.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(guestEmail))
                .collect(Collectors.toList());
    }

    private static Guest makeGuest() {
        Guest guest = new Guest();
        guest.setGuestId(1004);
        guest.setFirstName("Coco");
        guest.setLastName("Mochi");
        guest.setEmail("cocomochi@gmail.com");
        guest.setState("GA");
        return guest;
    }
}