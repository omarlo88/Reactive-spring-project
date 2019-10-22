package lo.omar.servicesBoutique;

import lo.omar.daoBoutique.SousCategorieRepository;
import lo.omar.entitiesBoutique.SousCategorie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SousCategorieImpl implements
        ICrudService<SousCategorie, String>, ICrudAddAndRemoveID<SousCategorie, String> {

    private final SousCategorieRepository sousCategorieRepository;

    public SousCategorieImpl(SousCategorieRepository sousCategorieRepository) {
        this.sousCategorieRepository = sousCategorieRepository;
    }


    @Override
    public Flux<SousCategorie> getAll() {
        return sousCategorieRepository.findAll();
    }

    @Override
    public Mono<SousCategorie> getById(String s) {
        return sousCategorieRepository.findById(s);
    }

    @Override
    public Mono<SousCategorie> saveEntity(SousCategorie entity) {
        return sousCategorieRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return sousCategorieRepository.deleteById(s);
    }

    @Override
    public Mono<Void> deleteAll() {
        return sousCategorieRepository.deleteAll();
    }

    public Flux<SousCategorie> getSousCategoriesByCategoriesIdsContains(String id){
        return sousCategorieRepository.findAllByCategorieIdsContains(id);
    }

    /*@Override
    public Mono<SousCategorie> removeId(String id1, String id2) {
        return sousCategorieRepository.findById(id1)
                .map(sousCategorie -> {
                    sousCategorie.getCategorieIds().removeIf(s -> s.equalsIgnoreCase(id2));
                    return sousCategorie;
                })
                .flatMap(sousCategorieRepository::save);
    }

    @Override
    public Mono<SousCategorie> addId(String id1, String id2) {
        return sousCategorieRepository.findById(id1)
                .map(sousCategorie -> {
                    sousCategorie.getCategorieIds().add(id2);
                    return sousCategorie;
                })
                .flatMap(sousCategorieRepository::save);
    }*/
}
