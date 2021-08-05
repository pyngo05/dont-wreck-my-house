package learn.dontwreckmyhouse.ui;

import learn.dontwreckmyhouse.data.DataException;
import learn.dontwreckmyhouse.domain.ReservationService;
import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;
import learn.dontwreckmyhouse.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Controller {

    private final ReservationService reservationService;
    private final View view;

    public Controller(ReservationService reservationService, View view) {
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House!");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
//                case VIEW_RESERVATIONS:
//                    viewByHost();
//                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
//                case CANCEL_RESERVATION:
//                    //cancelReservation();
//                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void editReservation() throws DataException {

        // get host id from user
        String hostId = view.getHostId();

        // with that host id from the user, get reservations from service layer
        Result<List<Reservation>> result = reservationService.findByHostId(UUID.fromString(hostId));
        if (!result.isSuccess()) {
            view.displayErrors(result.getErrorMessages());
            return;
        }

        // show reservations to user
        List<Reservation> reservations = result.getPayload();
        view.displayReservations(reservations);

        // ask user which reservation (id) they want to edit for that host
        int reservationId = view.getReservationId();

        // find chosen reservation
        Reservation reservation = null;
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId) {
                reservation = res;
                break;
            }
        }
        if (reservation == null) {
            view.displayError("No reservation with that ID.");
            return;
        }

        // ask user for new start and end date for that reservation
        LocalDate newStartDate = view.getNewStartDate();
        LocalDate newEndDate = view.getNewEndDate();

        // recalculate total
        reservation.setStartDate(newStartDate);
        reservation.setEndDate(newEndDate);
        Result<BigDecimal> calculationResult = reservationService.calculateReservationTotal(reservation);
        if (!calculationResult.isSuccess()) {
            view.displayErrors(calculationResult.getErrorMessages());
            return;
        }
        reservation.setTotal(calculationResult.getPayload());

        // show the user the proposed changes
        view.displayHeader("New reservation details:");
        view.displayReservation(reservation);

        // ask the user if they want to confirm the edit
        boolean doUpdate = view.getConfirmation("Confirm reservation update? [y/n]: ");

        // depending on choice, do the edit or do nothing
        if (doUpdate) {
            Result<Reservation> updateResult = reservationService.update(reservation);
            if (!updateResult.isSuccess()) {
                view.displayError("Failed to update reservation.");
                return;
            }
            view.displayHeader("Updated.");

        } else {
            view.displayHeader("Cancelled update.");
        }
    }

    //    private void viewByHost() {
//        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());
//        UUID hostId = view.getHostReservations();
//        Result<List<Reservation>> reservations = reservationService.findByHostId(hostId);
//        view.displayHeader("Reservations");
//        view.displayReservations(reservations);
//        view.enterToContinue();
//    }
//
//    // top level menu
//    private void viewByDate() {
//        LocalDate date = view.getForageDate();
//        List<Forage> forages = forageService.findByDate(date);
//        view.displayForages(forages);
//        view.enterToContinue();
//    }
//
//    private void viewItems() {
//        view.displayHeader(MainMenuOption.VIEW_ITEMS.getMessage());
//        Category category = view.getItemCategory();
//        List<Item> items = itemService.findByCategory(category);
//        view.displayHeader("Items");
//        view.displayItems(items);
//        view.enterToContinue();
//    }
//
//
    private void addReservation() throws DataException {

        // get host id from user
        String hostId = view.getHostId();

        //get guest id from user
        String guestId = view.getGuestId();

        // with that host id from the user, get reservations from service layer
        Result<List<Reservation>> result = reservationService.findByHostId(UUID.fromString(hostId));
        if (!result.isSuccess()) {
            view.displayErrors(result.getErrorMessages());
            return;
        }

        // show future reservations to user
        List<Reservation> reservations = result.getPayload();
        view.displayFutureReservations(reservations);

        // ask user for start and end date for that reservation
        LocalDate startDate = view.getNewStartDate();
        LocalDate endDate = view.getNewEndDate();

        // calculate total
        Reservation reservation = new Reservation();
//        TODO SET ALL BELOW TO RESERVATION
//        private int reservationId;
//        private BigDecimal total;
//        private Host host;
//        private Guest guest;
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setGuestId(Integer.parseInt(guestId));
        reservation.setHostId(UUID.fromString(hostId));
        Result<BigDecimal> calculationResult = reservationService.calculateReservationTotal(reservation);
        if (!calculationResult.isSuccess()) {
            view.displayErrors(calculationResult.getErrorMessages());
            return;
        }
        reservation.setTotal(calculationResult.getPayload());

        // show the user the details
        view.displayHeader("Reservation details:");
        view.displayNewReservation(reservation);

        // ask the user if they want to confirm
        boolean addReservation = view.getConfirmation("Confirm reservation? [y/n]: ");

        // depending on choice, do the edit or do nothing
        if (addReservation) {
            Result<Reservation> addNewReservation = reservationService.add(reservation);
            if (!addNewReservation.isSuccess()) {
                view.displayError("Failed to add reservation.");
                return;
            }
            view.displayHeader("Added new reservation.");

        } else {
            view.displayHeader("Cancelled new reservation.");
        }
    }


//        view.displayHeader(MainMenuOption.ADD_RESERVATION.getMessage());
//        Guest guest = getGuest();
//        if (guest == null) {
//            return;
//        }
//        Host host = getHost();
//        if (host == null) {
//            return;
//        }
//        Reservation reservation = view.makeReservation(reservation, host);
//        Result<Reservation> result = reservationService.add(reservation);
//        if (!result.isSuccess()) {
//            view.displayStatus(false, result.getErrorMessages());
//        } else {
//            String successMessage = String.format("Reservation %s created.", result.getPayload().getReservationId());
//            view.displayStatus(true, successMessage);
//        }
//    }
//
//    // support methods
//    private Guest getGuest() {
//        String lastNamePrefix = view.getForagerNamePrefix();
//        List<Forager> foragers = foragerService.findByLastName(lastNamePrefix);
//        return view.chooseForager(foragers);
//    }

    }
