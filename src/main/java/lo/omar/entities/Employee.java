package lo.omar.entities;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
@Document
public class Employee {

    @Id
    private String id;
    private String nom, prenom;
    private String email;
    private Double salaire;
    @DBRef
    private Fonction fonction;
    @DBRef
    private Departement departement;
}
