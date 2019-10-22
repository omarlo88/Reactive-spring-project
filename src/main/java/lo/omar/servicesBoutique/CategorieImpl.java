package lo.omar.servicesBoutique;

import lo.omar.daoBoutique.CategorieRepository;
import lo.omar.entitiesBoutique.Categorie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategorieImpl implements ICrudService<Categorie, String> {
    /*@Autowired
    private CategorieRepository categorieRepository;*/

    private final CategorieRepository categorieRepository;

    public CategorieImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Flux<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Mono<Categorie> getById(String s) {
        return categorieRepository.findById(s);
    }

    @Override
    public Mono<Categorie> saveEntity(Categorie entity) {
        return categorieRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return categorieRepository.deleteById(s);
    }

    @Override
    public Mono<Void> deleteAll() {
        return categorieRepository.deleteAll();
    }
}
