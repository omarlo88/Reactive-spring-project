package lo.omar.webServices;

import lo.omar.dao.EmployeeRepository;
import lo.omar.entities.Departement;
import lo.omar.entities.Employee;
import lo.omar.entities.Fonction;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
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

    public Mono<ServerResponse> getEmployee(ServerRequest request){
        //return ServerResponse.ok().body(employeeRepository.findById(request.pathVariable("id")), Employee.class);
        return employeeRepository.findById(request.pathVariable("id"))
                .flatMap(employee -> ServerResponse.ok().body(BodyInserters.fromObject(employee)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getEmployeeByEmail(ServerRequest request){
        return ServerResponse.ok().body(employeeRepository.getByEmail(request.pathVariable("email")), Employee.class);
    }

    public Mono<ServerResponse> getEmployeesByDepartement(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Departement> departementMono = Mono.justOrEmpty(new Departement(id, null, null, null));
        return ServerResponse.ok().body(employeeRepository.getAllByDepartement(departementMono), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getEmployeesByDepartementOrderByNom(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Departement> departementMono = Mono.just(new Departement(id, null, null, null));
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("5"));
        return ServerResponse.ok().body(employeeRepository.getAllByDepartementOrderByNom(departementMono,
                PageRequest.of(page, size)),
                Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getEmployeesByFonctionOrderByNom(ServerRequest request){
        Mono<Fonction> fonctionMono = request.bodyToMono(Fonction.class);
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("5"));
        return ServerResponse.ok().body(employeeRepository.getAllByFonctionOrderByNom(fonctionMono,
                PageRequest.of(page, size)),
                Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllByNomContainingIgnoreCaseOrderByNom(ServerRequest request){
        String nom = request.queryParam("nom").orElse("");
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("5"));
        return ServerResponse.ok().body(employeeRepository.getAllByNomContainingIgnoreCaseOrderByNom(nom,
                PageRequest.of(page, size)), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllByNomStartingWithIgnoreCaseOrderByNom(ServerRequest request){
        String nom = request.queryParam("nom").orElse("");
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("5"));
        return ServerResponse.ok().body(employeeRepository.getAllByNomStartingWithIgnoreCaseOrderByNom(nom,
                PageRequest.of(page, size)), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllBySalaireBetween(ServerRequest request){
        double salaire1 = Double.parseDouble(request.queryParam("salaire1").orElse("0"));
        double salaire2 = Double.parseDouble(request.queryParam("salaire2").orElseThrow(() -> new RuntimeException("not found!")));
        return ServerResponse.ok().body(employeeRepository.getAllBySalaireBetween(salaire1, salaire2), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllBySalaireEquals(ServerRequest request){
        double salaire = Double.parseDouble(request.queryParam("salaire").orElseThrow(() -> new RuntimeException("not found!")));
        return ServerResponse.ok().body(employeeRepository.getAllBySalaireEquals(salaire), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllBySalaireLessThan(ServerRequest request){
        double salaire = Double.parseDouble(request.queryParam("salaire").orElseThrow(() -> new RuntimeException("not found!")));
        return ServerResponse.ok().body(employeeRepository.getAllBySalaireLessThan(salaire), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllBySalaireGreaterThan(ServerRequest request){
        double salaire = Double.parseDouble(request.queryParam("salaire").orElseThrow(() -> new RuntimeException("not found!")));
        return ServerResponse.ok().body(employeeRepository.getAllBySalaireGreaterThan(salaire), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllByNomAndAndPrenom(ServerRequest request){
        String nom = request.queryParam("nom").orElse("");
        String prenom = request.queryParam("prenom").orElse("");
        return ServerResponse.ok().body(employeeRepository.getAllByNomAndAndPrenomAllIgnoreCaseOrderByNom(nom, prenom), Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveEmployee(ServerRequest request){
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class)
                .flatMap(employeeRepository::save);
        return ServerResponse.ok().body(employeeMono, Employee.class);
    }

    public Mono<ServerResponse> deleteEmployee(ServerRequest request){
        return ServerResponse.noContent().build(employeeRepository.deleteById(request.pathVariable("id")))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Employee> employeeNew = request.bodyToMono(Employee.class);
        return employeeRepository.findById(id)
                .flatMap(emplOld -> ServerResponse.ok().body(BodyInserters.fromPublisher(
                        employeeNew
                        .map(e -> {
                            emplOld.setNom(e.getNom());
                            emplOld.setPrenom(e.getPrenom());
                            emplOld.setSalaire(e.getSalaire());
                            emplOld.setEmail(e.getEmail());
                            emplOld.setDepartement(e.getDepartement());
                            emplOld.setFonction(e.getFonction());
                            return emplOld;
                        })
                        .flatMap(employeeRepository::save),
                        Employee.class)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
