package lo.omar.dao;

import lo.omar.entities.Departement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface DepartementRepository extends ReactiveMongoRepository<Departement, String> {

    //@Tailable //Ce cursor reste toujours ouvert
    //Flux<Departement> findAllBy();
    Mono<Departement> getByNom(String nom);
    Flux<Departement> findAllByNomLikeIgnoreCaseOrderByNom(@Param("nom") String nom);
    Flux<Departement> findAllByNomStartingWithIgnoreCaseOrderByNom(String nom);
    Flux<Departement> findAllByNomContainingIgnoreCaseOrderByNom(String nom);
}
