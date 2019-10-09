package lo.omar.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
@Document
public class Departement {

    @Id
    private String id;
    private String nom;
    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee chef;
    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Employee> employees;
}
