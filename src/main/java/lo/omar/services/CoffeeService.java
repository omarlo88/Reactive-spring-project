package lo.omar.services;

import lo.omar.dao.CoffeeRepository;
import lo.omar.entities.Coffee;
import lo.omar.entities.CoffeeOrder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Flux<Coffee> getCoffees(){
        return coffeeRepository.findAll();
    }

    public Mono<Coffee> getCoffee(String id){
        return coffeeRepository.findById(id);
    }

    public Mono<Void> deleteCoffees(){
        return coffeeRepository.deleteAll();
    }

    public Mono<Void> deleteCoffee(String id){
        return coffeeRepository.deleteById(id);
    }

    public Mono<Coffee> saveCoffee(Mono<Coffee> coffeeMono){
        return coffeeMono.flatMap(coffeeRepository::save);
    }

    public Mono<Coffee> updateCoffee(Coffee coffee){
        return coffeeRepository.save(coffee);
    }

    public Flux<CoffeeOrder> getCoffeeOrders(String coffeId){
        return Flux.<CoffeeOrder>generate(sink -> sink.next(new CoffeeOrder(coffeId, Instant.now())))
                .delayElements(Duration.ofSeconds(1));
    }
}
