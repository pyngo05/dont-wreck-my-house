package learn.dontwreckmyhouse.ui;

import learn.dontwreckmyhouse.models.Reservation;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class View {

    private final ConsoleIO io;

    // comparator to sort reservations by date
    private Comparator<Reservation> byDate = new Comparator<Reservation>() {
        public int compare(Reservation r1, Reservation r2) {
            if (r1.getStartDate().isBefore(r2.getStartDate())) return -1;
            else return 1;
        }
    };

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getHostId() {
        String hostId = io.readString("Host ID: ");
        return hostId;
    }

    public String getGuestId() {
        String guestId = io.readString("Guest ID: ");
        return guestId;
    }

    public int getReservationId() {
        int reservationId = io.readInt("Reservation ID: ");
        return reservationId;
    }

    public LocalDate getNewStartDate() {
        LocalDate newStartDate = io.readLocalDate("Start Date: ");
        return newStartDate;
    }

    public LocalDate getNewEndDate() {
        LocalDate newEndDate = io.readLocalDate("End Date: ");
        return newEndDate;
    }

    public boolean getConfirmation(String prompt) {
        return io.readBoolean(prompt);
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayErrors(List<String> messages) {
        displayHeader("Error:");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayError(String message) {
        displayHeader("Error:");
        io.println(message);
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }

        // Sorting
        Collections.sort(reservations, byDate);

        for (Reservation reservation : reservations) {
            displayReservation(reservation);
        }
    }

    public void displayFutureReservations(List<Reservation> reservations) {
        LocalDate today = LocalDate.now();
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }

        // Sorting
        Collections.sort(reservations, byDate);

        int pastReservations = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getEndDate().isAfter(today)) {
                displayReservation(reservation);
            } else {
                pastReservations++;
            }
        }

        // notify the user about all reservations being in the past
        if (pastReservations == reservations.size()) {
            io.println("No future reservations found.");
        }
    }

    public void displayReservation(Reservation reservation) {
        if (reservation == null) {
            return;
        }
        io.printf("%s: %s - %s (guest %s). Total: $%.2f%n",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal()
        );
    }

    public void displayNewReservation(Reservation reservation) {
        if (reservation == null) {
            return;
        }
        io.printf("%s - %s (guest %s). Total: $%.2f%n",
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuestId(),
                reservation.getTotal()
        );
    }

}
