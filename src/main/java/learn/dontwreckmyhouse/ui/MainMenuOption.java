package learn.dontwreckmyhouse.ui;

public enum MainMenuOption {

    EXIT(0, "Exit"),
    EDIT_RESERVATION(1, "Edit a Reservation"),
    ADD_RESERVATION(2, "Add a Reservation"),
    VIEW_RESERVATIONS(3, "View Reservations by Host"),
    CANCEL_RESERVATION(4, "Delete a Reservation");

    private int value;
    private String message;


    private MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;

    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
