package tn.itbs.projet.entities;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduit;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String nom;
    
    
    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;
    
    
    @Positive(message = "Le prix doit être positif")
    private double prix;
    
    
    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseur;
    
    
    @Min(value = 0, message = "Le seuil minimum ne peut pas être négatif")
    private int seuilMin;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<MouvementStock> mouvements = new ArrayList<>();
}