package lo.omar.webServicesBoutique;

import lo.omar.entitiesBoutique.CommandeProduit;
import lo.omar.servicesBoutique.CommandeProduitImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class CommandeProduitHandler {

    private final CommandeProduitImpl commandeProduit;

    public CommandeProduitHandler(CommandeProduitImpl commandeProduit) {
        this.commandeProduit = commandeProduit;
    }

    public Mono<ServerResponse> getCommandesProduits(ServerRequest request){
        return ServerResponse.ok().body(commandeProduit.getAll(), CommandeProduit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCommandesProduit(ServerRequest request){
        return commandeProduit.getById(request.pathVariable("id"))
                .flatMap(c -> ServerResponse.ok().body(BodyInserters.fromObject(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCommandesProduitByStatut(ServerRequest request){
        return ServerResponse.ok().body(commandeProduit.getCommandesByStatut(request.pathVariable("statut")),
                CommandeProduit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCommandesProduitByDate(ServerRequest request){
        return ServerResponse.ok().body(commandeProduit
                        .getCommandesByDate(LocalDateTime.parse(request.pathVariable("date"))),
                CommandeProduit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCommandesProduitByDateBetween(ServerRequest request){
        //String date1 = request.queryParam("date1").orElseThrow(RuntimeException::new);
        //String date2 = request.queryParam("date1").orElseThrow(RuntimeException::new);
        //request.queryParam("date1").ifPresent(System.out::println);

        String date1 = request.queryParam("date1").orElseThrow(RuntimeException::new);
        String date2 = request.queryParam("date1").orElse(String.valueOf(LocalDateTime.now()));
        return ServerResponse.ok().body(commandeProduit
                        .getCommandesByDateBetween(LocalDateTime.parse(date1), LocalDateTime.parse(date2)),
                CommandeProduit.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveCommande(ServerRequest request){
        return ServerResponse.ok().body(
                request.bodyToMono(CommandeProduit.class)
                .flatMap(commandeProduit::saveEntity), CommandeProduit.class);
    }

    public Mono<ServerResponse> updateCommandeProduit(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<CommandeProduit> commandeProduitNew = request.bodyToMono(CommandeProduit.class);
        return commandeProduit.getById(id)
                .flatMap(commandeOld -> ServerResponse.ok().body(
                        BodyInserters.fromPublisher(
                                commandeProduitNew
                                .map(c -> {
                                    commandeOld.setProduitId(c.getProduitId());
                                    commandeOld.setStatut(c.getStatut());
                                    commandeOld.setDate(c.getDate());
                                    return commandeOld;
                                })
                                .flatMap(commandeProduit::saveEntity),
                                CommandeProduit.class
                        )
                ))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCommandeProduit(ServerRequest request){
        return ServerResponse.noContent().build(commandeProduit.deleteById(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
