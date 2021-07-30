package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.DataException;
import learn.dontwreckmyhouse.data.ReservationRepository;
import learn.dontwreckmyhouse.data.GuestRepository;
import learn.dontwreckmyhouse.data.HostRepository;
import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;

import java.time.LocalDate;
import java.util.*;
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

    //TODO should this be date?
    public List<Reservation> findByDate(LocalDate date) {

        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));
        Map<UUID, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHostId(), i -> i));

        List<Reservation> result = reservationRepository.findByDate(date);
        for (Reservation reservation : result) {
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
            reservation.setHost(hostMap.get(reservation.getHost().getHostId()));
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

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);

        if (reservation.getReservationId() <= 0) {
            result.addErrorMessage("Reservation id is required.");
        }

        if (result.isSuccess()) {
            if (reservationRepository.update(reservation)) {
                result.setPayload(reservationRepository.update(reservation));
            } else {
                String message = String.format("Reservation id %s was not found.", reservation.getReservationId()));
                result.addErrorMessage(message);
            }
        }
        return result;
    }

    // TODO fix this
//    public Result<Reservation> deleteById(int reservationId) throws DataException {
//        Result<Reservation> result = new MemoryResult();
//        if (!repository.deleteById(memoryId)) {
//            String message = String.format("Memory id %s was not found.", memoryId);
//            result.addErrorMessage(message);
//        }
//        return result;
//    }

    private Result<Reservation> validate(Reservation reservation) throws DataException {

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
            result.addErrorMessage("Reservation date is required.");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is required.");
        }
        return result;
    }

    private Result<Reservation> validateFields(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        // No past dates.
        if (reservation.getDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation date cannot be in the past.");
        }

        if (reservation.getGuest() == null || reservation.getHost() == null
                || reservation.getStartDate() == null || reservation.getEndDate()== null ) {
            result.addErrorMessage("Invalid reservation. Please re-enter reservation details.");
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
