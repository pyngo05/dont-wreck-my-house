package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.GuestRepositoryDouble;
import learn.dontwreckmyhouse.data.HostRepositoryDouble;
import learn.dontwreckmyhouse.data.ReservationRepository;
import learn.dontwreckmyhouse.data.ReservationRepositoryDouble;
import learn.dontwreckmyhouse.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service;

    @BeforeEach
    void setupTest() {
        ReservationRepositoryDouble reservationRepo = new ReservationRepositoryDouble();
        GuestRepositoryDouble guestRepo = new GuestRepositoryDouble();
        HostRepositoryDouble hostRepo = new HostRepositoryDouble();
        service = new ReservationService(reservationRepo, guestRepo, hostRepo);
    }

//    @Test
//    void findByDate() {
//    }
//
//    @Test
//    void add() {
//    }
//
//    @Test
//    void update() {
//    }

    @Test
    void findByHostId() {
        Result<List<Reservation>> result = service.findByHostId(UUID.fromString("4e52e660-3112-4d4f-9089-827819afa5de"));
        assertTrue(result.isSuccess());
        assertEquals(2, result.getPayload().size());
    }

//    @Test
//    void testAdd() {
//    }
//
//    @Test
//    void testUpdate() {
//    }
//
//    @Test
//    void deleteById() {
//    }
}