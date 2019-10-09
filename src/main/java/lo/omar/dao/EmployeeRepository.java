package lo.omar.dao;

import lo.omar.entities.Departement;
import lo.omar.entities.Employee;
import lo.omar.entities.Fonction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

    Flux<Employee> getAllByDepartement(Mono<Departement> dept);
    Flux<Employee> getAllByDepartementOrderByNom(Mono<Departement> dept, Pageable pageable);
    Flux<Employee> getAllByFonctionOrderByNom(Mono<Fonction> fonction, Pageable pageable);
    Flux<Employee> getAllByNomContainingIgnoreCaseOrderByNom(String nom, Pageable pageable);
    Flux<Employee> getAllByNomStartingWithIgnoreCaseOrderByNom(@Param("nom") String nom, Pageable pageable);
    Flux<Employee> getAllBySalaireBetween(Double s1, Double S2);
    Flux<Employee> getAllBySalaireEquals(Double salaire);
    Flux<Employee> getAllBySalaireLessThan(Double salaire);
    Flux<Employee> getAllBySalaireGreaterThan(Double salaire);
    Flux<Employee> getByNomAndAndPrenomAllIgnoreCaseOrderByNom(String nom, String prenom);
    Mono<Employee> getByEmail(String email);
    //@Tailable //Ce cursor reste toujours ouvert
    //Flux<Employee> findAllBy();
}
