package gq.vaccum121.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by vaccum121 on 11.04.16.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);

}