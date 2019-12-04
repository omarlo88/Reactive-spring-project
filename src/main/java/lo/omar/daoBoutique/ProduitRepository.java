package lo.omar.daoBoutique;

import lo.omar.entitiesBoutique.Produit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProduitRepository extends ReactiveMongoRepository<Produit, String> {
    Flux<Produit> findAllByCategorieIdsContainsOrderByPrixAsc(String id);
    Flux<Produit> findAllByNomLikeIgnoreCaseOrderByPrixAsc(String nom);
    Flux<Produit> findAllByNomContainsOrderByPrixAsc(String nom);
    Flux<Produit> findAllByPrixOrderByPrixAsc(double prix);
    Flux<Produit> findAllByPrixBetweenOrderByPrixAsc(double prix1, double prix2);
    Flux<Produit> findAllByPromotionOrderByPrixAsc(String promotion);
}
