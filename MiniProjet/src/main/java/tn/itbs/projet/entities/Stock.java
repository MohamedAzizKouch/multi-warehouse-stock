package tn.itbs.projet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStock;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    @Min(value = 0, message = "Le seuil d'alerte ne peut pas être négatif")
    private Integer seuilAlerte;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;

    @NotNull(message = "L'entrepôt est obligatoire")
    @ManyToOne
    @JoinColumn(name = "idEntrepot")
    private Entrepot entrepot;
}