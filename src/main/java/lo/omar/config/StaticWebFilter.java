package lo.omar.config;

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
