package lo.omar.entitiesBoutique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeProduitEvent {

    private CommandeProduit commandeProduit;
    private Instant dateCommande;
}
