package lo.omar.entitiesBoutique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "produits")
public class Produit {

    @Id
    private String id;
    private List<String> categorieIds = new ArrayList<>();
    private String nom;
    private String description;
    private String couleur;
    private double prix;
    private String image;
    private String statut;
    private boolean promotion;
    private double rabais;
    private boolean available;
}
