package lo.omar;

import lo.omar.dao.CoffeeRepository;
import lo.omar.dao.DepartementRepository;
import lo.omar.dao.EmployeeRepository;
import lo.omar.dao.FonctionRepository;
import lo.omar.entities.Coffee;
import lo.omar.entities.Departement;
import lo.omar.entities.Employee;
import lo.omar.entities.Fonction;
import lo.omar.entitiesBoutique.Categorie;
import lo.omar.entitiesBoutique.CommandeProduit;
import lo.omar.entitiesBoutique.Produit;
import lo.omar.entitiesBoutique.SousCategorie;
import lo.omar.servicesBoutique.CategorieImpl;
import lo.omar.servicesBoutique.CommandeProduitImpl;
import lo.omar.servicesBoutique.ProduitImpl;
import lo.omar.servicesBoutique.SousCategorieImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class ReactiveSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringProjectApplication.class, args);
    }

    /*@Bean
    CorsWebFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Possibly...
        // config.applyPermitDefaultValues()

        config.setAllowCredentials(true);
        // allow access to my dev Angular instance
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }*/


    /*@Bean
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
                    //.log();

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

    }*/

    @Bean
    CommandLineRunner initData(CategorieImpl categorie, SousCategorieImpl sousCategorie,
                               CommandeProduitImpl commandeProduit, ProduitImpl produit){
        return args -> {
            categorie.deleteAll()
                    .thenMany(Flux.just("Électroniques", "Électroménagère", "Alimentation", "Restaurant", "Autres")
                            .map(text -> new Categorie(null, text))
                            .flatMap(categorie::saveEntity)
                    )
                    .thenMany(categorie.getAll())
                    .subscribe(System.out::println);


            sousCategorie.deleteAll()
                    .thenMany(Flux.just("Apple", "Windows", "Autres")
                            .map(text -> new SousCategorie(null, null, text))
                            .flatMap(sousCategorie::saveEntity)
                    )
                    .thenMany(sousCategorie.getAll())
                    .subscribe(System.out::println);


            commandeProduit.deleteAll()
                    .thenMany(Flux.just("Encours de validation", "Validée", "En préparation pour livraison", "Livrée")
                            .map(text -> new CommandeProduit(null, null, text, LocalDateTime.now()))
                            .flatMap(commandeProduit::saveEntity)
                    )
                    .thenMany(commandeProduit.getAll())
                    .subscribe(System.out::println);


            produit.deleteAll()
                    .thenMany(Flux.interval(Duration.ofSeconds(1))
                            .take(11)
                            .map(i -> i.intValue() + 1)
                            .map(i -> {
                                Produit p = new Produit();
                                p.setId(UUID.randomUUID().toString());
                                p.setNom("Produit " + i);
                                p.setDescription("Produit test il faut changer le nom");
                                p.setPrix(i + 1.50);
                                p.setRabais(0.7);
                                if (i % 3 == 0) {
                                    p.setStatut("promo");
                                    p.setPromotion(true);
                                }
                                p.setImage(i.toString());
                                return p;
                            })
                            .flatMap(produit::saveEntity)
                    )
                    .thenMany(produit.getAll())
                    .subscribe(System.out::println);

        };

    }
}
