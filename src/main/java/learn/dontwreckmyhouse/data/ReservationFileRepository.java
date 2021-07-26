package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservationFileRepository {

    private static final String HEADER = "id,startDate,endDate,guestId,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    // Finds all reservations
    public List<Reservation> findAll() {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 4) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    // Find host's reservations by UUID
    public Reservation findByHostId(UUID hostId) throws DataException {
        List<Reservation> all = findAll();
        for (Reservation reservation : all) {
            if (reservation.getHostId() == hostId) {
                return reservation;
            }
        }
        return null;
    }

    // Find by reservation id
    public Reservation findById(int id) throws DataException {
        List<Reservation> all = findAll();
        for (Reservation reservation : all) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }


    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findAll();

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        all.add(reservation);
        writeToFile(all);

        return reservation;
    }

    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
                writeToFile(all, reservation.getId());
                return true;
            }
        }
        return false;
    }

    public void delete() {

    }

    public void serialize(Reservation reservation) {
        return String.format("%d,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotal());
    }

    public void deserialize() {

    }

    public void writeToFile() {
        Reservation reservation = new Reservation();
        reservation.setId(fields[0]);
        reservation.setDate(date);
        reservation.setKilograms(Double.parseDouble(fields[3]));

        Forager forager = new Forager();
        forager.setId(fields[1]);
        result.setForager(forager);

        Item item = new Item();
        item.setId(Integer.parseInt(fields[2]));
        result.setItem(item);
        return result;
    }
    }

    private String getFilePath(UUID id) {
        return Paths.get(directory, id + ".csv").toString();
    }

}