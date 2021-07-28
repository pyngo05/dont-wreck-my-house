package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.DataException;
import learn.dontwreckmyhouse.data.ReservationRepository;
import learn.dontwreckmyhouse.data.GuestRepository;
import learn.dontwreckmyhouse.data.HostRepository;
import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByDate(LocalDate date) {

        Map<String, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getguestId(), i -> i));
        Map<Integer, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHostId(), i -> i));

        List<Reservation> result = reservationRepository.findByDate(date);
        for (Reservation reservation : result) {
            reservation.setGuest(guestMap.get(reservation.getguest().getId()));
            reservation.setHost(hostMap.get(reservation.getItem().getId()));
        }

        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(reservationRepository.add(reservation));

        return result;
    }

    public int generate(LocalDate start, LocalDate end, int count) throws DataException {

        if (start == null || end == null || start.isAfter(end) || count <= 0) {
            return 0;
        }

        count = Math.min(count, 500);

        ArrayList<LocalDate> dates = new ArrayList<>();
        while (!start.isAfter(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }

        List<Host> hosts = hostRepository.findAll();
        List<Guest> guests = guestRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Reservation reservation = new Reservation();
            reservation.setDate(dates.get(random.nextInt(dates.size())));
            reservation.setForager(guests.get(random.nextInt(guests.size())));
            reservation.setItem(hosts.get(random.nextInt(hosts.size())));
            reservation.setKilograms(random.nextDouble() * 5.0 + 0.1);
            reservationRepository.add(reservation);
        }

        return count;
    }

    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result = validateFields(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result = validateChildrenExist(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (reservation.getDate() == null) {
            result.addErrorMessage("Forage date is required.");
        }

        if (reservation.getForager() == null) {
            result.addErrorMessage("Forager is required.");
        }

        if (reservation.getItem() == null) {
            result.addErrorMessage("Item is required.");
        }
        return result;
    }

    private Result<Reservation> validateFields(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        // No past dates.
        if (reservation.getDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation date cannot be in the past.");
        }

        if (reservation.getKilograms() <= 0 || reservation.getKilograms() > 250.0) {
            result.addErrorMessage("Kilograms must be a positive number less than 250.0");
        }

        List<Reservation> all = reservationRepository.findByDate(reservation.getDate());
        for (Reservation existingReservation : all) {
            if (existingReservation.getHost().getHostId() == reservation.getHost().getHostId()
                    && existingReservation.getGuest().getGuestId() == reservation.getGuest().getGuestId()) {
                result.addErrorMessage("This rental property has already been reserved by a guest on that date range.");

            }
        }

        return result;
    }

    // validate that guest and host exists
    private Result<Reservation> validateChildrenExist(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();

            if (reservation.getGuest().getGuestId() == 0
                || guestRepository.findByGuestId(reservation.getGuest().getGuestId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }

        if (hostRepository.findByHostId(reservation.getHost().getHostId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        return result;
    }

}
