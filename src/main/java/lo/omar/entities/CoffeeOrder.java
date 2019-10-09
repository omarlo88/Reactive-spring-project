package lo.omar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class CoffeeOrder {

    private String coffeeId;
    private Instant dateOrder;
}
