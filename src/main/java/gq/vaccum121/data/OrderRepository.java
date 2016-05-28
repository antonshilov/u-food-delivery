package gq.vaccum121.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by vaccum121 on 11.04.16.
 */
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStatus(Order.Status status);
}