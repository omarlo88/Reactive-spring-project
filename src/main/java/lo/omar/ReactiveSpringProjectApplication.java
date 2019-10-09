package lo.omar;

import lo.omar.dao.CoffeeRepository;
import lo.omar.dao.DepartementRepository;
import lo.omar.dao.EmployeeRepository;
import lo.omar.dao.FonctionRepository;
import lo.omar.entities.Coffee;
import lo.omar.entities.Departement;
import lo.omar.entities.Employee;
import lo.omar.entities.Fonction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class ReactiveSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(DepartementRepository departementRepository, FonctionRepository fonctionRepository,
                               EmployeeRepository employeeRepository, CoffeeRepository coffeeRepository){
        return args -> {
            departementRepository.deleteAll()
                    .thenMany(
                            Flux.just("Informatique", "RH", "Juridique", "Compta", "R&D", "Logistique")
                            .map(name -> new Departement(null, name, null, null))
                            .flatMap(departementRepository::save))
                    .thenMany(departementRepository.findAll())
                    .subscribe(System.out::println);

            fonctionRepository.deleteAll()
                    .thenMany(
                            Flux.just("Directeur", "Informaticien", "Développeur Backend", "Développeur Frontend",
                                    "Développeur Full-stack", "Business Analyst", "Stagiaire", "Juriste")
                            .map(name -> new Fonction(null, name, null))
                            .flatMap(fonctionRepository::save)
                    ).thenMany(fonctionRepository.findAll())
                    .subscribe(System.out::println);

            employeeRepository.deleteAll()
                    .delayElement(Duration.ofSeconds(5))
                    .thenMany(
                            Flux.just(new Employee(null, "Lo", "Omar", "omar@gmail.com", 7000.00,
                                    fonctionRepository.getByNom("Développeur Full-stack").block(),
                                    departementRepository.getByNom("Informatique").block()),
                                    new Employee(null, "Ben", "Bassim", "ben@gmail.com", 7000.00,
                                            fonctionRepository.getByNom("Développeur Backend").block(),
                                            departementRepository.getByNom("Informatique").block()),
                                    new Employee(null, "Sava", "Haoua", "sava@gmail.com", 8000.00,
                                            fonctionRepository.getByNom("Juriste").block(),
                                            departementRepository.getByNom("Juridique").block()))
                            .flatMap(employee -> employeeRepository.save(employee))
                    ).thenMany(employeeRepository.findAll())
                    .subscribe(System.out::println);



            employeeRepository.findAll()
                    .toStream()
                    .forEach(System.out::println);



            coffeeRepository.deleteAll()
                    .thenMany(Flux.just("Jet Black Mongo", "Darth Mongo", "Pit of Despair Mongo", "Black Alert Mongo")
                            .map(name -> new Coffee(UUID.randomUUID().toString(), name))
                            .flatMap(coffeeRepository::save)
                    ).thenMany(coffeeRepository.findAll())
                    .subscribe(System.out::println);

        };

    }

}
