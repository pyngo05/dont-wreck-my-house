//package learn.dontwreckmyhouse;
//
//import learn.dontwreckmyhouse.data.ForageFileRepository;
//import learn.dontwreckmyhouse.data.ForagerFileRepository;
//import learn.dontwreckmyhouse.data.ItemFileRepository;
//import learn.dontwreckmyhouse.domain.ForageService;
//import learn.dontwreckmyhouse.domain.ForagerService;
//import learn.dontwreckmyhouse.domain.ItemService;
//import learn.dontwreckmyhouse.ui.ConsoleIO;
//import learn.dontwreckmyhouse.ui.Controller;
//import learn.dontwreckmyhouse.ui.View;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class App {
//    public static void main(String[] args) {
//
//        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-configuration.xml");
//
//        ConsoleIO io = new ConsoleIO();
//        View view = container.getBean(View.class);
//
//        ForageFileRepository forageFileRepository = container.getBean(ForageFileRepository.class );
//        ForagerFileRepository foragerFileRepository = container.getBean(ForagerFileRepository.class);
//        ItemFileRepository itemFileRepository = container.getBean(ItemFileRepository.class);
//
//        ForagerService foragerService = container.getBean(ForagerService.class);
//        ForageService forageService = container.getBean(ForageService.class);
//        ItemService itemService = container.getBean(ItemService.class);
//
//        Controller controller = container.getBean(Controller.class);
//        controller.run();
//    }
//}
