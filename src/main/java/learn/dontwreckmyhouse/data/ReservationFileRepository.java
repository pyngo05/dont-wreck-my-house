package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,startDate,endDate,guestId,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public Result<List<Reservation>> findByDateRange(LocalDate startDate, LocalDate endDate, UUID hostId) {
        ArrayList<Reservation> overlappingReservations = new ArrayList<>();

        Result<List<Reservation>> result = findByHostId(hostId);
        for (Reservation reservation : result.getPayload()) {
            if (reservation.getStartDate().isAfter(endDate) || reservation.getEndDate().isBefore(startDate)) {
                // reservation does not overlap start and end date, so skip it
                continue;
            } else {
                overlappingReservations.add(reservation);
            }
        }

        return new Result<>(overlappingReservations);
    }

    @Override
    // Find host's reservations by host ID
    public Result<List<Reservation>> findByHostId(UUID hostId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String filepath = getFilePath(hostId);
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    reservations.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
            return new Result<>("Failed to read reservations from file.");
        }

        return new Result<>(reservations);
    }

    @Override
    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<List<Reservation>> result = findByHostId(reservation.getHostId());
        if (!result.isSuccess()) {
            return new Result<>("Failed to get reservations from file.");
        }

        List<Reservation> all = result.getPayload();
        int nextId = getNextId(all);
        reservation.setReservationId((nextId));
        all.add(reservation);
        writeToFile(all, reservation.getHostId());
        return new Result<>(reservation);
    }


    @Override
    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<List<Reservation>> result = findByHostId(reservation.getHostId());
        if (!result.isSuccess()) {
            return new Result<>("Failed to get reservations from file.");
        }

        List<Reservation> all = result.getPayload();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.set(i, reservation);
                writeToFile(all, reservation.getHostId());
                break;
            }
        }

        return new Result<>(reservation);

    }

    @Override
    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<List<Reservation>> result = findByHostId(reservation.getHostId());
        if (!result.isSuccess()) {
            return new Result<>("Failed to get reservations from file.");
        }

        List<Reservation> all = result.getPayload();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.remove(i);      // remove
                writeToFile(all, reservation.getHostId());
                break;
            }
        }

        return new Result<>(reservation);

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

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, UUID HostId) {
        Reservation result = new Reservation();
        result.setHostId(HostId);
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setGuestId(Integer.parseInt(fields[3]));
        result.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));
        return result;
    }

    private void writeToFile(List<Reservation> reservations, UUID hostId) throws DataException {
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

    // Returns true if date is between start and end date, inclusively.
    private boolean dateIsBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date.equals(start) || date.equals(end)) {
            return true;
        }
        if (date.isBefore(end) && date.isAfter(start)) {
            return true;
        }
        return false;
    }

}