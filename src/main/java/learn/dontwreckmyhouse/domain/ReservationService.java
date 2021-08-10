package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.DataException;
import learn.dontwreckmyhouse.data.ReservationRepository;
import learn.dontwreckmyhouse.data.GuestRepository;
import learn.dontwreckmyhouse.data.HostRepository;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Host;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {

        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public Result<List<Reservation>> findByHostId(UUID hostId) {
        if (hostId == null) {
            return new Result<>("Invalid host ID.");
        }

        Result<List<Reservation>> result = reservationRepository.findByHostId(hostId);
        if (!result.isSuccess()) {
            return new Result<>("Failed to get reservations for that host ID.");
        }

        // get host that is shared by all these reservations
        Result<Host> hostResult = hostRepository.findByHostId(hostId);
        if (!hostResult.isSuccess()) {
            return new Result<>("Failed to get host.");
        }

        // set each reservation's host and guest using their hostID and guestId respectively
        List<Reservation> reservations = result.getPayload();
        for (Reservation res : reservations) {
            Result<Guest> guestResult = guestRepository.findByGuestId(res.getGuestId());
            if (!guestResult.isSuccess()) {
                return new Result<>("Failed to get guest.");
            }
            res.setGuest(guestResult.getPayload());

            res.setHost(hostResult.getPayload());
        }

        return new Result<>(reservations);
    }

    public Result<BigDecimal> calculateReservationTotal(Reservation reservation) {
        // get host
        Result<Host> result = hostRepository.findByHostId(reservation.getHostId());
        if (!result.isSuccess()) {
            return new Result<>("Failed to find host with that ID.");
        }

        // get host's rates
        Host host = result.getPayload();
        BigDecimal standardRate = host.getStandardRate();
        BigDecimal weekendRate = host.getWeekendRate();

        // check days booked and their cost
        LocalDate currentDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        BigDecimal total = BigDecimal.valueOf(0);
        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                total = total.add(weekendRate);
            } else {
                total = total.add(standardRate);
            }

            currentDate = currentDate.plusDays(1);
        }

        return new Result<>(total);
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return new Result<>("Invalid reservation.");
        }

        Result<Reservation> addResult = reservationRepository.add(reservation);
        if (!addResult.isSuccess()) {
            return new Result<>("Failed to add reservation!");
        }

        return new Result<>(reservation);
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return new Result<>("Invalid reservation.");
        }

        result = reservationRepository.update(reservation);
        if (!result.isSuccess()) {
            return new Result<>("Failed to update reservation.");
        }

        return new Result<>(reservation);
    }

    public int getNextId(List<Reservation> reservations) {
        int maxId = 0;
        for (Reservation reservation : reservations) {
            if (maxId < reservation.getReservationId()) {
                maxId = reservation.getReservationId();
            }
        }
        return maxId + 1;
    }

    public Result<Reservation> deleteById(Reservation reservation) throws DataException {

        Result<Reservation> result = reservationRepository.delete(reservation);
        if (!result.isSuccess()) {
            return new Result<>("Failed to delete reservation.");
        }

        return new Result<>(reservation);
    }

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

//        Result<Reservation> result = validateNulls(reservation);

        if (reservation == null) {
//            result.addErrorMessage("Nothing to save.");
            return new Result<>("Nothing to save.");
        }

        if (reservation.getStartDate() == null) {
            //result.addErrorMessage("Reservation start date is required.");
            return new Result<>("Reservation start date is required.");
        }

        if (reservation.getEndDate() == null) {
//            result.addErrorMessage("Reservation end date is required.");
            return new Result<>("Reservation end date is required.");
        }

        if (reservation.getGuest() == null) {
//            result.addErrorMessage("Guest is required.");
            return new Result<>("Guest is required.");
        }

        if (reservation.getHost() == null) {
//            result.addErrorMessage("Host is required.");
            return new Result<>("Host is required.");
        }
        return new Result<>(reservation);
    }

    private Result<Reservation> validateFields(Reservation reservation) {

        //The start date must be in the future and cannot cancel a reservation that's in the past.
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            return new Result<>("Reservation date cannot be in the past.");

        }

        //The start date must come before the end date.
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            return new Result<>("Reservation start date cannot be after end date.");
        }

        //Guest, host, and start and end dates are required.
        if (reservation.getGuest() == null || reservation.getHost() == null
                || reservation.getStartDate() == null || reservation.getEndDate() == null) {
            return new Result<>("Invalid reservation. Please re-enter reservation details.");
        }

        //The reservation must not overlap existing reservation dates.
        Result<List<Reservation>> findByDateResult = reservationRepository.findByDateRange(reservation.getStartDate(), reservation.getEndDate(), reservation.getHostId());
        if (!findByDateResult.isSuccess()) {
            return new Result<>("Invalid date range.");
        }
        if (findByDateResult.getPayload().size() > 0) {
            return new Result<>("Reservations exist between those dates.");
        }

        return new Result<>(reservation);
    }

    // validate that guest and host exists
    private Result<Reservation> validateChildrenExist(Reservation reservation) throws DataException {

        if (reservation.getGuest().getGuestId() == 0
                || guestRepository.findByGuestId(reservation.getGuest().getGuestId()) == null) {
            return new Result<>("Guest does not exist.");
        }

        if (hostRepository.findByHostId(reservation.getHost().getHostId()) == null) {
            return new Result<>("Host does not exist.");
        }

        return new Result<>(reservation);
    }

}
