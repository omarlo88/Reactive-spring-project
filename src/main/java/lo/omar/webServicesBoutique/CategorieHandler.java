package lo.omar.webServicesBoutique;

import lo.omar.entitiesBoutique.Categorie;
import lo.omar.servicesBoutique.CategorieImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CategorieHandler {

    private final CategorieImpl categorie;

    public CategorieHandler(CategorieImpl categorie) {
        this.categorie = categorie;
    }


    public Mono<ServerResponse> getCategories(ServerRequest request){
        return ServerResponse.ok().body(categorie.getAll(), Categorie.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCategorie(ServerRequest request){
        return categorie.getById(request.pathVariable("id"))
                .flatMap(cat -> ServerResponse.ok().body(BodyInserters.fromObject(cat)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveCategorie(ServerRequest request){
        return ServerResponse.ok().body(
                request.bodyToMono(Categorie.class)
                .flatMap(categorie::saveEntity),
                Categorie.class);
    }

    public Mono<ServerResponse> updateCategorie(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Categorie> catNew = request.bodyToMono(Categorie.class);
        return categorie.getById(id)
                .flatMap(cateOld -> ServerResponse.ok().body(BodyInserters.fromPublisher(
                        catNew
                        .map(c -> {
                            cateOld.setNom(c.getNom());
                            return cateOld;
                        })
                        .flatMap(categorie::saveEntity),
                        Categorie.class)
                ))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCategorie(ServerRequest request){
        return ServerResponse.noContent().build(categorie.deleteById(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
