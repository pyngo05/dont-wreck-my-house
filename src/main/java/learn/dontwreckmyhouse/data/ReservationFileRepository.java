package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationFileRepository {

    private static final String HEADER = "id,startDate,endDate,guestId,total";
    private final String directory;
    private final String delimiter = ",";

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    // Find host's reservations by host ID
    public List<Reservation> findByHostId(UUID hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        String filepath = getFilePath(hostId);
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    // Finds reservations by id
    public Reservation findByReservationId(int reservationId, UUID hostId) throws DataException {
        List<Reservation> all = findByHostId(hostId);
        for (Reservation reservation : all) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }


    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHostId());
        int nextId = getNextId(all);
        reservation.setReservationId((nextId));
        all.add(reservation);
        writeToFile(all, reservation.getHostId());
        return reservation;
    }

    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHostId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.set(i, reservation);
                writeToFile(all, reservation.getHostId());
                return true;
            }
        }
        return false;
    }

    public boolean delete(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHostId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.remove(i);      // remove
                writeToFile(all, reservation.getHostId());
                return true;
            }
        }
        return false;
    }

    private int getNextId(List<Reservation> reservations) {
        int maxId = 0;
        for (Reservation reservation : reservations) {
            if (maxId < reservation.getReservationId()) {
                maxId = reservation.getReservationId();
            }
        }
        return maxId + 1;
    }

    public String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal());
    }

    public Reservation deserialize(String[] fields, UUID HostId) {
        Reservation result = new Reservation();
        result.setHostId(HostId);
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setGuestId(Integer.parseInt(fields[3]));
        result.setTotal(BigDecimal.valueOf(Long.parseLong(fields[4])));
        return result;
    }

    public void writeToFile(List<Reservation> reservations, UUID hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String getFilePath(UUID hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

}