package lo.omar.entitiesBoutique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor @ToString
@Document(collection = "commandesProduit")
public class CommandeProduit {

    @Id
    private String id;
    private List<String> produitId = new ArrayList<>();
    private String statut;
    private LocalDateTime date;
}
