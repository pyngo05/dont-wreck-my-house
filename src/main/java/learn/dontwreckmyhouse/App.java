package learn.dontwreckmyhouse;

import learn.dontwreckmyhouse.data.ReservationFileRepository;
import learn.dontwreckmyhouse.data.GuestFileRepository;
import learn.dontwreckmyhouse.data.HostFileRepository;
import learn.dontwreckmyhouse.domain.ReservationService;
import learn.dontwreckmyhouse.ui.ConsoleIO;
import learn.dontwreckmyhouse.ui.Controller;
import learn.dontwreckmyhouse.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
//    public static void main(String[] args) {
//
//        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-configuration.xml");
//
//        ConsoleIO io = new ConsoleIO();
//        View view = container.getBean(View.class);
//
//        ReservationFileRepository reservationFileRepository = container.getBean(ReservationFileRepository.class );
//        GuestFileRepository guestFileRepository = container.getBean(GuestFileRepository.class);
//        HostFileRepository hostFileRepository = container.getBean(HostFileRepository.class);
//
//        ReservationService reservationService = container.getBean(ReservationService.class);
//
//        Controller controller = container.getBean(Controller.class);
//        controller.run();
//    }

    // TODO replace this main with proper bean xml version above^
    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("./data/reservations");

        GuestFileRepository guestFileRepository = new GuestFileRepository("./data/guests.csv");
        HostFileRepository hostFileRepository = new HostFileRepository("./data/hosts.csv");
        ReservationService reservationService = new ReservationService(reservationFileRepository, guestFileRepository, hostFileRepository);

        Controller controller = new Controller(reservationService, view);
        controller.run();
    }
}
