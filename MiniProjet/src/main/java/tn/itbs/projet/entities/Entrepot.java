package tn.itbs.projet.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrepot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrepot;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @Positive(message = "La capacité doit être positive")
    private int capacite;

    @OneToMany(mappedBy = "entrepot")
    @JsonIgnore
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "entrepot")
    @JsonIgnore
    private List<MouvementStock> mouvements = new ArrayList<>();
}