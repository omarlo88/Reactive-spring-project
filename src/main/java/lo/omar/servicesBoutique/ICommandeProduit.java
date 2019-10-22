package lo.omar.servicesBoutique;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;


public interface ICommandeProduit<T, ID> {
    Flux<T> getCommandesByStatut(String statut);
    Flux<T> getCommandesByDate(LocalDateTime date);
    Flux<T> getCommandesByDateBetween(LocalDateTime date1, LocalDateTime date2);
}
