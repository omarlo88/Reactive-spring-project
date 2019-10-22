package lo.omar.servicesBoutique;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrudService<T, ID> {

    Flux<T> getAll();
    Mono<T> getById(ID id);
    Mono<T> saveEntity(T entity);
    Mono<Void> deleteById(ID id);
    Mono<Void> deleteAll();
}
