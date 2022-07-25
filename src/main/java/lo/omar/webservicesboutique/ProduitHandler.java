package lo.omar.webservicesboutique;

import lo.omar.entitiesBoutique.Produit;
import lo.omar.servicesBoutique.ProduitImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProduitHandler {

    private final ProduitImpl produit;

    public ProduitHandler(ProduitImpl produit) {
        this.produit = produit;
    }

    public Mono<ServerResponse> getProduits(ServerRequest request){
        return ServerResponse.ok().body(produit.getAll(), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduit(ServerRequest request){
        return produit.getById(request.pathVariable("id"))
            .flatMap(p -> ServerResponse.ok().body(BodyInserters.fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduitsByCategorieId(ServerRequest request){
        return ServerResponse.ok().body(produit.getProduitsByCategorieId(request.pathVariable("idCat")), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduitsByNom(ServerRequest request){
        String nom = request.queryParam("nom").orElse("");
        return ServerResponse.ok().body(produit.getProduitsByNom(nom), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduitsByPrix(ServerRequest request){
        String prix = request.queryParam("prix").orElse("0.0");
        return ServerResponse.ok().body(produit.getProduitsByPrix(Double.parseDouble(prix)), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduitsByPrixBetween(ServerRequest request){
        String prix1 = request.queryParam("prix1").orElse("0.0");
        String prix2 = request.queryParam("prix2").orElse("0.0");
        return ServerResponse.ok().body(produit.getProduitsByPrixBetween(Double.parseDouble(prix1), Double.parseDouble(prix2)),
                Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProduitsByPromotion(ServerRequest request){
        String promotion = request.queryParam("promotion").orElse("");
        return ServerResponse.ok().body(produit.getProduitsByPromotion(promotion), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveProduit(ServerRequest request){
        return ServerResponse.ok().body(
                request.bodyToMono(Produit.class)
                .flatMap(produit::saveEntity), Produit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateProduit(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Produit> produitNew = request.bodyToMono(Produit.class);
        return produit.getById(id)
                .flatMap(prodOld -> ServerResponse.ok().body(
                        BodyInserters.fromPublisher(
                                produitNew
                                .map(p -> {
                                    prodOld.setNom(p.getNom());
                                    prodOld.setDescription(p.getDescription());
                                    prodOld.setCategorieIds(p.getCategorieIds());
                                    prodOld.setCouleur(p.getCouleur());
                                    prodOld.setImage(p.getImage());
                                    prodOld.setPrix(p.getPrix());
                                    prodOld.setPromotion(p.isPromotion());
                                    prodOld.setStatut(p.getStatut());
                                    prodOld.setRabais(p.getRabais());
                                    return prodOld;
                                })
                                .flatMap(produit::saveEntity),
                                Produit.class)
                ))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProduit(ServerRequest request){

        /*return ServerResponse.noContent().build(produit.deleteById(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());*/

        return produit.getById(request.pathVariable("id"))
                .flatMap(produit1 -> ServerResponse.ok().build(produit.deleteById(produit1.getId())))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
