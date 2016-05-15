package gq.vaccum121.data;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by vaccum121 on 11.04.16.
 */
public interface DishRepository extends MongoRepository<Dish, String> {

    Dish findByName(String firstName);
}