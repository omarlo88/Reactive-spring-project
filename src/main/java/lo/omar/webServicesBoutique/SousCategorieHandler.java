package lo.omar.webServicesBoutique;

import lo.omar.entitiesBoutique.SousCategorie;
import lo.omar.servicesBoutique.SousCategorieImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SousCategorieHandler {

    private final SousCategorieImpl sousCategorie;

    public SousCategorieHandler(SousCategorieImpl sousCategorie) {
        this.sousCategorie = sousCategorie;
    }


    public Mono<ServerResponse> getSousCategories(ServerRequest request){
        return ServerResponse.ok().body(sousCategorie.getAll(), SousCategorie.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getSousCategorie(ServerRequest request){
        return sousCategorie.getById(request.pathVariable("id"))
                .flatMap(cat -> ServerResponse.ok().body(BodyInserters.fromObject(cat)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getSousCategoriesByCategoriesIdsContains(ServerRequest request){
        return ServerResponse.ok().body(sousCategorie.getSousCategoriesByCategoriesIdsContains(request.pathVariable("idCat")),
                SousCategorie.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveSousCategorie(ServerRequest request){
        return ServerResponse.ok().body(
                request.bodyToMono(SousCategorie.class)
                .flatMap(sousCategorie::saveEntity),
                SousCategorie.class
        );
    }

    public Mono<ServerResponse> updateSousCategorie(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<SousCategorie> scatNew = request.bodyToMono(SousCategorie.class);
        return sousCategorie.getById(id)
                .flatMap(scateOld -> ServerResponse.ok().body(BodyInserters.fromPublisher(
                        scatNew
                        .map(c -> {
                            scateOld.setNom(c.getNom());
                            scateOld.setCategorieIds(c.getCategorieIds());
                            return scateOld;
                        })
                        .flatMap(sousCategorie::saveEntity),
                        SousCategorie.class)
                ))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteSousCategorie(ServerRequest request){
        return sousCategorie.getById(request.pathVariable("id"))
                .flatMap(sousCat -> ServerResponse.ok().build(sousCategorie.deleteById(sousCat.getId())))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
