package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Result<List<Guest>> findAll() {
        ArrayList<Guest> guests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    guests.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            return new Result<>("Failed to read guests from file.");
        }

        return new Result<>(guests);
    }

    @Override
    public Result<Guest> findByGuestId(int guestId) {
        Result<List<Guest>> findResult = findAll();
        if (!findResult.isSuccess()) {
            return new Result<>("Failed to find all guests.");
        }

        List<Guest> allGuests = findResult.getPayload();
        for (Guest guest : allGuests) {
            if (guest.getGuestId() == guestId) {
                return new Result<>(guest);
            }
        }

        return new Result<>("Guest not found with that ID.");
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }

}