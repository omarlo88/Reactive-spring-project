package lo.omar.servicesBoutique;

import lo.omar.daoBoutique.ProduitRepository;
import lo.omar.entitiesBoutique.Produit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProduitImpl implements ICrudService<Produit, String>, ICrudAddAndRemoveID<Produit, String> {

    private final ProduitRepository produitRepository;

    public ProduitImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

   /* @Override
    public Mono<Produit> removeId(String id1, String id2) {
        return produitRepository.findById(id1)
                .map(p -> {
                    p.getCategorieId().removeIf(s -> s.equalsIgnoreCase(id2));
                    return p;
                })
                .flatMap(produitRepository::save);
    }

    @Override
    public Mono<Produit> addId(String id1, String id2) {
        return produitRepository.findById(id1)
                .map(p -> {
                    p.getCategorieId().add(id2);
                    return p;
                })
                .flatMap(produitRepository::save);
    }
*/
   public Flux<Produit> getProduitsByCategorieId(String id){
       return produitRepository.findAllByCategorieIdsContainsOrderByPrixAsc(id);
   }

    public Flux<Produit> getProduitsByNom(String nom){
        return produitRepository.findAllByNomLikeIgnoreCaseOrderByPrixAsc(nom);
    }

    public Flux<Produit> getProduitsByPrix(double prix){
        return produitRepository.findAllByPrixOrderByPrixAsc(prix);
    }

    public Flux<Produit> getProduitsByPrixBetween(double prix1, double prix2){
        return produitRepository.findAllByPrixBetweenOrderByPrixAsc(prix1, prix2);
    }

    public Flux<Produit> getProduitsByPromotion(String promotion){
        return produitRepository.findAllByPromotionOrderByPrixAsc(promotion);
    }

    @Override
    public Flux<Produit> getAll() {
        return produitRepository.findAll();
    }

    @Override
    public Mono<Produit> getById(String s) {
        return produitRepository.findById(s);
    }

    @Override
    public Mono<Produit> saveEntity(Produit entity) {
        return produitRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return produitRepository.deleteById(s);
    }

    @Override
    public Mono<Void> deleteAll() {
        return produitRepository.deleteAll();
    }
}
