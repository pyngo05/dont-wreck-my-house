package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.GuestRepository;
import learn.dontwreckmyhouse.models.Guest;
import learn.dontwreckmyhouse.models.Reservation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

//    public List<Guest> findByEmail(String email) {
//        return repository.findByEmail(email);
//    }

//    public Result<Guest> findByGuestId(int guestId) {
//        if (guestId <=0) {
//            return new Result<>("Invalid guest ID.");
//        }
//
//        Result<Guest> result = repository.findByGuestId(guestId);
//        if (!result.isSuccess()) {
//            return new Result<>("Failed to find guest with that ID.");
//        }
//
//        return new Result<>(result.getPayload());
//    }

//    private Result<Guest> validateNulls(Guest guest) {
//        Result<Guest> result = new Result<>();
//
//        if (guest == null) {
//            result.addErrorMessage("Nothing to save.");
//            return result;
//        }
//
//        if (guest.getFirstName() == null) {
//            result.addErrorMessage("Guest first name is required.");
//        }
//
//        if (guest.getLastName() == null) {
//            result.addErrorMessage("Guest last name is required.");
//        }
//
//        if (guest.getEmail() == null) {
//            result.addErrorMessage("Guest email is required.");
//        }
//
//        if (guest.getPhone() == null) {
//            result.addErrorMessage("Guest phone number is required.");
//        }
//
//        if (guest.getState() == null) {
//            result.addErrorMessage("Guest state is required.");
//        }
//        return result;
//    }
    //
//    private Result<Guest> validateFields(Guest guest, Result<Guest> result) {
//        // No duplicates.
//        List<Guest> all = repository.findAll();
//        for (Guest guests : all) {
//            if (guests.getFirstName().equals(guest.getFirstName())
//                    && guests.getLastName().equals(guest.getLastName())
//                    && guests.getEmail().equals(guest.getEmail())
//                    && guests.getPhone().equals(guest.getPhone())
//                    && guests.getState().equals(guest.getState())) {
//                result.addErrorMessage("This guest already exists.");
//            }
//        } return result;
//    }
}