package lo.omar.daoBoutique;

import lo.omar.entitiesBoutique.Categorie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategorieRepository extends ReactiveMongoRepository<Categorie, String> {
}
