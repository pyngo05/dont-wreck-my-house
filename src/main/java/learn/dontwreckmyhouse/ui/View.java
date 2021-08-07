package learn.dontwreckmyhouse.ui;

import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Host;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class View {

    private final ConsoleIO io;

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

//    public LocalDate getForageDate() {
//        displayHeader(MainMenuOption.VIEW_FORAGES_BY_DATE.getMessage());
//        return io.readLocalDate("Select a date [MM/dd/yyyy]: ");
//    }
//
//    public String getForagerNamePrefix() {
//        return io.readRequiredString("Forager last name starts with: ");
//    }
//
//    public Forager chooseForager(List<Forager> foragers) {
//        if (foragers.size() == 0) {
//            io.println("No foragers found");
//            return null;
//        }
//
//        int index = 1;
//        for (Forager forager : foragers.stream().limit(25).collect(Collectors.toList())) {
//            io.printf("%s: %s %s%n", index++, forager.getFirstName(), forager.getLastName());
//        }
//        index--;
//
//        if (foragers.size() > 25) {
//            io.println("More than 25 foragers found. Showing first 25. Please refine your search.");
//        }
//        io.println("0: Exit");
//        String message = String.format("Select a forager by their index [0-%s]: ", index);
//
//        index = io.readInt(message, 0, index);
//        if (index <= 0) {
//            return null;
//        }
//        return foragers.get(index - 1);
//    }
//
//    public Category getItemCategory() {
//        displayHeader("Item Categories");
//        int index = 1;
//        for (Category c : Category.values()) {
//            io.printf("%s: %s%n", index++, c);
//        }
//        index--;
//        String message = String.format("Select a Category [1-%s]: ", index);
//        return Category.values()[io.readInt(message, 1, index) - 1];
//    }

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

//    public Item chooseItem(List<Item> items) {
//
//        displayItems(items);
//
//        if (items.size() == 0) {
//            return null;
//        }
//
//        int itemId = io.readInt("Select an item id: ");
//        Item item = items.stream()
//                .filter(i -> i.getId() == itemId)
//                .findFirst()
//                .orElse(null);
//
//        if (item == null) {
//            displayStatus(false, String.format("No item with id %s found.", itemId));
//        }
//
//        return item;
//    }
//
//    public Forage makeForage(Forager forager, Item item) {
//        Forage forage = new Forage();
//        forage.setForager(forager);
//        forage.setItem(item);
//        forage.setDate(io.readLocalDate("Forage date [MM/dd/yyyy]: "));
//        String message = String.format("Kilograms of %s: ", item.getName());
//        forage.setKilograms(io.readDouble(message, 0.001, 250.0));
//        return forage;
//    }
//
//    public Item makeItem() {
//        displayHeader(MainMenuOption.ADD_ITEM.getMessage());
//        Item item = new Item();
//        item.setCategory(getItemCategory());
//        item.setName(io.readRequiredString("Item Name: "));
//        item.setDollarPerKilogram(io.readBigDecimal("$/Kg: ", BigDecimal.ZERO, new BigDecimal("7500.00")));
//        return item;
//    }
//
//    public Forager makeForager() {
//        displayHeader(MainMenuOption.ADD_FORAGER.getMessage());
//        Forager forager = new Forager();
//        forager.setFirstName(io.readRequiredString("First Name: "));
//        forager.setLastName(io.readRequiredString("Last Name: "));
//        forager.setState(io.readRequiredString("State: "));
//        return forager;
//    }
//
//    public GenerateRequest getGenerateRequest() {
//        displayHeader(MainMenuOption.GENERATE.getMessage());
//        LocalDate start = io.readLocalDate("Select a start date [MM/dd/yyyy]: ");
//        if (start.isAfter(LocalDate.now())) {
//            displayStatus(false, "Start date must be in the past.");
//            return null;
//        }
//
//        LocalDate end = io.readLocalDate("Select an end date [MM/dd/yyyy]: ");
//        if (end.isAfter(LocalDate.now()) || end.isBefore(start)) {
//            displayStatus(false, "End date must be in the past and after the start date.");
//            return null;
//        }
//
//        GenerateRequest request = new GenerateRequest();
//        request.setStart(start);
//        request.setEnd(end);
//        request.setCount(io.readInt("Generate how many random forages [1-500]?: ", 1, 500));
//        return request;
//    }
//
//    public void enterToContinue() {
//        io.readString("Press [Enter] to continue.");
//    }

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

//    public void displayStatus(boolean success, String message) {
//        displayStatus(success, List.of(message));
//    }

//    public void displayStatus(boolean success, List<String> messages) {
//        displayHeader(success ? "Success" : "Error");
//        for (String message : messages) {
//            io.println(message);
//        }
//    }

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

        for (int i=0; i<reservations.size(); i++)

        for (Reservation reservation : reservations) {
            displayReservation(reservation);
        }
    }

        Comparator<Reservation> byDate = new Comparator<Reservation>() {
        public int compare(Reservation r1, Reservation r2) {
            if (r1.getStartDate().isBefore(r2.getStartDate())) return -1;
            else return 1;
        }
    };

    public void displayFutureReservations(List<Reservation> reservations) {
        LocalDate today = LocalDate.now();
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }

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


//    public void displayForagers(List<Forager> foragers) {
//        if (foragers == null || foragers.isEmpty()) {
//            io.println("No foragers found.");
//            return;
//        }
//        for (Forager forager : foragers) {
//            io.printf("%s %s%n",
//                    forager.getFirstName(),
//                    forager.getLastName()
//            );
//        }
//    }
//
//    public void displayItems(List<Item> items) {
//
//        if (items.size() == 0) {
//            io.println("No items found");
//        }
//
//        for (Item item : items) {
//            io.printf("%s: %s, %s, %.2f $/kg%n", item.getId(), item.getName(), item.getCategory(), item.getDollarPerKilogram());
//        }
//    }
//
//    public void displayItemWeights(Map<Item, Double> itemWeights) {
//        if (itemWeights == null || itemWeights.isEmpty()) {
//            io.println("No items.");
//            return;
//        }
//
//        io.println("Item ID\tItem Name\tWeight (kg)");
//        itemWeights.forEach((item, weight) -> io.printf("%d\t\t%s\t%f\n", item.getId(), item.getName(), weight));
//    }
//
//    public void displayCategoryValues(Map<Category, Double> categoryValues) {
//        if (categoryValues == null || categoryValues.isEmpty()) {
//            io.println("No categories.");
//            return;
//        }
//
//        io.println("Category\tTotal Value ($)");
//        categoryValues.forEach((category, value) -> io.printf("%s\t%f\n", category.name(), value));
//    }
}
