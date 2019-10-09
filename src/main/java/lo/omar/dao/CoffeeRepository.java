package lo.omar.dao;

import lo.omar.entities.Coffee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CoffeeRepository extends ReactiveMongoRepository<Coffee, String> {
}
