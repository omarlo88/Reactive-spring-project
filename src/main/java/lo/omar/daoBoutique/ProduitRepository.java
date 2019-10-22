package lo.omar.daoBoutique;

import lo.omar.entitiesBoutique.Produit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProduitRepository extends ReactiveMongoRepository<Produit, String> {
    Flux<Produit> findAllByCategorieIdsContainsOrderByPrixDesc(String id);
    Flux<Produit> findAllByNomLikeOrderByPrixDesc(String nom);
    Flux<Produit> findAllByPrixOrderByPrixDesc(double prix);
    Flux<Produit> findAllByPrixBetweenOrderByPrixDesc(double prix1, double prix2);
    Flux<Produit> findAllByPromotionOrderByPrixDesc(String promotion);
}
