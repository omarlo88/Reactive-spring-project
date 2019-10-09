package lo.omar.webServices;

import lo.omar.dao.DepartementRepository;
import lo.omar.entities.Departement;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@Slf4j
public class DepartementHandler {

    private final DepartementRepository departementRepository;

    public DepartementHandler(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    public Mono<ServerResponse> getDepartements(ServerRequest serverRequest){
        return ServerResponse.ok()//.contentType(MediaType.TEXT_EVENT_STREAM)
                .body(departementRepository.findAll(), Departement.class);
    }

    public Mono<ServerResponse> getDepartementsStream(ServerRequest request){
        log.info(" Stream " + request.path());
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(departementRepository.findAll(), Departement.class);
    }


    public Mono<ServerResponse> getDepartement(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return departementRepository.findById(id)
                .flatMap(departement -> ServerResponse.ok().body(fromObject(departement)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> rechercherDepartementByQuery(ServerRequest request){
        String nom = request.queryParam("nom").orElseThrow(RuntimeException::new);
        return departementRepository.getByNom(nom)
                .flatMap(departement -> ServerResponse.ok().body(fromObject(departement)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveDepartement(ServerRequest request){
        Mono<Departement> departement = request.bodyToMono(Departement.class)
                .flatMap(departementRepository::save);
        return ServerResponse.ok().body(departement, Departement.class);
    }

    public Mono<ServerResponse> updateDepartement(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Departement> deptNew = request.bodyToMono(Departement.class);
        return departementRepository.findById(id)
                .flatMap(deptOld ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromPublisher(deptNew
                                                //.map(dept -> new Departement(id, dept.getNom(), dept.getChef(), dept.getEmployees()))
                                                .map(dept -> {
                                                    deptOld.setNom(dept.getNom());
                                                    deptOld.setChef(dept.getChef());
                                                    deptOld.setEmployees(dept.getEmployees());
                                                    return deptOld;
                                                })
                                                .flatMap(departementRepository::save)
                                        , Departement.class))
                ).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteDepartement(ServerRequest request){
        return ServerResponse.noContent().build(departementRepository.deleteById(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> chercherDepartementByNomIgnoreCase(ServerRequest request){
        String nom = request.queryParam("nom").orElseThrow(() -> new RuntimeException("Not Found!!"));
        return ServerResponse.ok().body(
                departementRepository
                        .findAllByNomContainingIgnoreCaseOrderByNom(nom), Departement.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> chercherDepartementByNomStartingWithIgnoreCase(ServerRequest request){
        String nom = request.queryParam("nom").orElseThrow(() -> new RuntimeException("Not Found!!"));
        /*return ServerResponse.ok().body(BodyInserters.fromPublisher(
                departementRepository.findAllByNomStartingWithIgnoreCaseOrderByNom(nom), Departement.class))
                .switchIfEmpty(ServerResponse.notFound().build());*/
        return ServerResponse.ok().body(
                departementRepository.findAllByNomStartingWithIgnoreCaseOrderByNom(nom), Departement.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> chercherDepartementByNomLikeWithIgnoreCase(ServerRequest request){
        String nom = request.queryParam("nom").orElseThrow(() -> new RuntimeException("Not Found!!"));
        return ServerResponse.ok().body(departementRepository.findAllByNomLikeIgnoreCaseOrderByNom(nom), Departement.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

