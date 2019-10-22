package lo.omar.daoBoutique;

import lo.omar.entitiesBoutique.SousCategorie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SousCategorieRepository extends ReactiveMongoRepository<SousCategorie, String> {
    Flux<SousCategorie> findAllByCategorieIdsIn(List<String> ids);
    Flux<SousCategorie> findAllByCategorieIdsContains(String id);
}
