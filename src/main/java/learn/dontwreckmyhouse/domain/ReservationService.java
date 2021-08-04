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
//    private final GuestRepository guestRepository;
//    private final HostRepository hostRepository;

//    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
//        this.guestRepository = guestRepository;
//        this.hostRepository = hostRepository;
    }

    public Result<List<Reservation>> findByHostId(UUID hostId) {

        // TODO later: validations, is ID null, etc

        Result<List<Reservation>> result = reservationRepository.findByHostId(hostId);
        if (!result.isSuccess()) {
            return new Result<>("Failed to get reservations for that host ID.");
        }

        return new Result<>(result.getPayload());
    }

//    public Result<Reservation> add(Reservation reservation) throws DataException {
//        Result<Reservation> result = validate(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        result.setPayload(reservationRepository.add(reservation));
//
//        return result;
//    }
//
//    public Result<Reservation> update(Reservation reservation) throws DataException {
//        Result<Reservation> result = new Result<Reservation>();
//
//        if (reservation.getReservationId() <= 0) {
//            result.addErrorMessage("Reservation id is required.");
//            return result;
//        }
//
//        result = validate(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        boolean updated = reservationRepository.update(reservation);
//        if (updated) {
//            result.setPayload(reservation);
//        } else {
//            result.addErrorMessage("Reservation failed to update.");
//        }
//
//        return result;
//    }
//
//    public Result<Reservation> deleteById(int reservationId, UUID hostId) throws DataException {
//        Result<Reservation> result = new Result<Reservation>();
//
//        // get reservation to make sure it exists
//        Reservation reservation = reservationRepository.findByReservationId(reservationId, hostId);
//        if (reservation == null) {
//            result.addErrorMessage("Reservation not found for that host.");
//            return result;
//        }
//
//        // delete it
//        boolean deleted = reservationRepository.delete(reservation);
//        if (deleted) {
//            result.setPayload(reservation);
//        } else {
//            result.addErrorMessage("Reservation failed to delete.");
//        }
//
//        return result;
//    }
//
//    private Result<Reservation> validate(Reservation reservation) throws DataException {
//
//        Result<Reservation> result = validateNulls(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        result = validateFields(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        result = validateChildrenExist(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        return result;
//    }
//
//    private Result<Reservation> validateNulls(Reservation reservation) {
//        Result<Reservation> result = new Result<>();
//
//        if (reservation == null) {
//            result.addErrorMessage("Nothing to save.");
//            return result;
//        }
//
//        if (reservation.getStartDate() == null) {
//            result.addErrorMessage("Reservation start date is required.");
//        }
//
//        if (reservation.getEndDate() == null) {
//            result.addErrorMessage("Reservation end date is required.");
//        }
//
//        if (reservation.getGuest() == null) {
//            result.addErrorMessage("Guest is required.");
//        }
//
//        if (reservation.getHost() == null) {
//            result.addErrorMessage("Host is required.");
//        }
//        return result;
//    }
//
//    private Result<Reservation> validateFields(Reservation reservation) {
//        Result<Reservation> result = new Result<>();
//
//        //The start date must be in the future and cannot cancel a reservation that's in the past.
//        if (reservation.getStartDate().isBefore(LocalDate.now())) {
//            result.addErrorMessage("Reservation date cannot be in the past.");
//            return result;
//        }
//
//        //The start date must come before the end date.
//        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
//            result.addErrorMessage("Reservation start date cannot be after end date.");
//            return result;
//        }
//
//
//        //Guest, host, and start and end dates are required.
//        if (reservation.getGuest() == null || reservation.getHost() == null
//                || reservation.getStartDate() == null || reservation.getEndDate()== null ) {
//            result.addErrorMessage("Invalid reservation. Please re-enter reservation details.");
//            return result;
//        }
//
//        //The reservation must not overlap existing reservation dates.
//        List<Reservation> reservationsOnThoseDates = reservationRepository.findByDateRange(reservation.getStartDate(), reservation.getEndDate(), reservation.getHostId());
//        if (reservationsOnThoseDates.size() > 0) {
//            result.addErrorMessage("Reservations exist between those dates.");
//        }
//
//        return result;
//    }
//
//    // validate that guest and host exists
//    private Result<Reservation> validateChildrenExist(Reservation reservation) throws DataException {
//        Result<Reservation> result = new Result<>();
//
//            if (reservation.getGuest().getGuestId() == 0
//                || guestRepository.findByGuestId(reservation.getGuest().getGuestId()) == null) {
//            result.addErrorMessage("Guest does not exist.");
//        }
//
//        if (hostRepository.findByHostId(reservation.getHost().getHostId()) == null) {
//            result.addErrorMessage("Host does not exist.");
//        }
//
//        return result;
//    }

}
