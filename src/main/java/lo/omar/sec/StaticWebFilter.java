package lo.omar.sec;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/*@Component // Cette classe est utile si l'app angular est en production
public class StaticWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/")){
            return chain.filter(
                    exchange
                    .mutate()
                    .request(exchange.getRequest()
                    .mutate()
                            .path("/index.html")
                    .build())
                    .build());

        }
        return chain.filter(exchange);
    }
}*/
