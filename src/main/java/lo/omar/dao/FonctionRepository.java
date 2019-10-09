package lo.omar.dao;

import lo.omar.entities.Fonction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FonctionRepository extends ReactiveMongoRepository<Fonction, String> {

    Mono<Fonction> getByNom(String nom);
}
