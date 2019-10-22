package lo.omar.servicesBoutique;

import lo.omar.daoBoutique.CommandeProduitRepository;
import lo.omar.entitiesBoutique.CommandeProduit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class CommandeProduitImpl implements ICrudService<CommandeProduit, String>,
        ICommandeProduit<CommandeProduit, String>, ICrudAddAndRemoveID<CommandeProduit, String>{

    private final CommandeProduitRepository commandeProduitRepository;

    public CommandeProduitImpl(CommandeProduitRepository commandeProduitRepository) {
        this.commandeProduitRepository = commandeProduitRepository;
    }

    @Override
    public Flux<CommandeProduit> getCommandesByStatut(String statut) {
        return commandeProduitRepository.findAllByStatutContainingIgnoreCase(statut);
    }

    @Override
    public Flux<CommandeProduit> getCommandesByDate(LocalDateTime date) {
        return commandeProduitRepository.findAllByDate(date);
    }

    @Override
    public Flux<CommandeProduit> getCommandesByDateBetween(LocalDateTime date1, LocalDateTime date2) {
        return commandeProduitRepository.findAllByDateBetween(date1, date2);
    }
/*
    @Override
    public Mono<CommandeProduit> removeId(String id1, String id2) {
        return commandeProduitRepository.findById(id1)
                .map(c -> {
                    c.getProduitId().removeIf(s -> s.equalsIgnoreCase(id2));
                    return c;
                })
                .flatMap(commandeProduitRepository::save);
    }

   @Override
    public Mono<CommandeProduit> addId(String id1, String id2) {
        return commandeProduitRepository.findById(id1)
                .map(c -> {
                    c.getProduitId().add(id2);
                    return c;
                })
                .flatMap(commandeProduitRepository::save);
    }*/

    @Override
    public Flux<CommandeProduit> getAll() {
        return commandeProduitRepository.findAll();
    }

    @Override
    public Mono<CommandeProduit> getById(String s) {
        return commandeProduitRepository.findById(s);
    }

    @Override
    public Mono<CommandeProduit> saveEntity(CommandeProduit entity) {
        return commandeProduitRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return commandeProduitRepository.deleteById(s);
    }

    @Override
    public Mono<Void> deleteAll() {
        return commandeProduitRepository.deleteAll();
    }
}
