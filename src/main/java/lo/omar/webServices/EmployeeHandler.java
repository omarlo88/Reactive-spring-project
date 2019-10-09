package lo.omar.webServices;

import lo.omar.dao.EmployeeRepository;
import lo.omar.entities.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EmployeeHandler {

    private final EmployeeRepository employeeRepository;

    public EmployeeHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<ServerResponse> getEmployees(ServerRequest request){
        return ServerResponse.ok().body(employeeRepository.findAll(), Employee.class);
    }
}
