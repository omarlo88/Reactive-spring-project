package lo.omar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor @AllArgsConstructor @ToString
public class Coffee {

    private String id;
    private String coffeeName;
}
