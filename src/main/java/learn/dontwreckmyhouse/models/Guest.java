package learn.dontwreckmyhouse.models;

public class Guest {

    private int guestId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String state;

    public int getGuestId() {
        return guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getState() {
        return state;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

}