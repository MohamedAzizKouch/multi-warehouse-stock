package tn.itbs.projet.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduit;

    private String nom;
    private String categorie;
    private double prix;
    private String fournisseur;
    private int seuilMin;

    @OneToMany(mappedBy = "produit")
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "produit")
    private List<MouvementStock> mouvements = new ArrayList<>();
}