package lo.omar.daoBoutique;

import lo.omar.entitiesBoutique.CommandeProduit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface CommandeProduitRepository extends ReactiveMongoRepository<CommandeProduit, String> {
    Flux<CommandeProduit> findAllByDate(LocalDateTime dateTime);
    Flux<CommandeProduit> findAllByDateBetween(LocalDateTime date1, LocalDateTime date2);
    Flux<CommandeProduit> findAllByStatutContainingIgnoreCase(String statut);
}
