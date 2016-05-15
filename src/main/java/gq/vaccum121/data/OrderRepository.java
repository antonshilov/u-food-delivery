package gq.vaccum121.data;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by vaccum121 on 11.04.16.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

}