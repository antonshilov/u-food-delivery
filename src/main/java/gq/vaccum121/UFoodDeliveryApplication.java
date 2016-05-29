package gq.vaccum121;

import gq.vaccum121.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class UFoodDeliveryApplication implements CommandLineRunner {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  DishRepository dishRepository;

    public static void main(String[] args) {
        SpringApplication.run(UFoodDeliveryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();
        dishRepository.deleteAll();
        // save a couple of customers
        repository.save(new Customer("Alice", "Smith","89138488","Krasnoyarsk"));
        repository.save(new Customer("Bob", "Smith","8800888080","Moscow"));
        repository.save(new Customer("Maggy", "Preper","12345567","Kraken"));

        // save food and drink
        dishRepository.save(new Dish("Pizza",400,1000));
        dishRepository.save(new Dish("Ice Cream",20,10));
        dishRepository.save(new Dish("Coca-Cola",100,200));
        dishRepository.save(new Dish("Bread",100,5));
        dishRepository.save(new Dish("Spaghetti",150,390));
        dishRepository.save(new Dish("Tea",150,100));
        dishRepository.save(new Dish("Chicken",500,1300));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        repository.findAll().forEach(System.out::println);
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
        orderRepository.deleteAll();
        Order order = new Order(LocalDateTime.now(), LocalDateTime.now());
        order.getDishes().add(new Dish("gg", "Hui", 150, 200));
        order.getDishes().add(new Dish("sas", "Zhopa", 160, 500));
        orderRepository.save(order);
        orderRepository.findAll().forEach(System.out::println);

    }
}
