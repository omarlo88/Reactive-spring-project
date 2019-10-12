package lo.omar.webServices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RouterEmployeeConfig {

    @Bean
    RouterFunction<?> routerFunctionEmployee(EmployeeHandler handler){
        return RouterFunctions.route()
                .path("/api", builder -> builder
                        .GET("/employees",  handler::getEmployees)
                        .GET("/{id}", handler::getEmployee)
                        .GET("/byEmail/{email}", handler::getEmployeeByEmail)
                        .GET("/byDepartement/{id}", handler::getEmployeesByDepartement)
                )
                .build();
    }
}
