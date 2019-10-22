package lo.omar.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor @ToString
public class Fonction {

    @Id
    private String id;
    private String nom;
    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Employee> employees = new ArrayList<>();
}
