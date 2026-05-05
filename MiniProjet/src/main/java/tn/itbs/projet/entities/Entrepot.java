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
public class Entrepot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrepot;

    private String nom;
    private String adresse;
    private int capacite;

    @OneToMany(mappedBy = "entrepot")
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "entrepot")
    private List<MouvementStock> mouvements = new ArrayList<>();
}