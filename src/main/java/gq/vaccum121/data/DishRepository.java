package gq.vaccum121.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by vaccum121 on 11.04.16.
 */
@RepositoryRestResource(collectionResourceRel = "dishes", path = "dishes")
public interface DishRepository extends MongoRepository<Dish, String> {

}