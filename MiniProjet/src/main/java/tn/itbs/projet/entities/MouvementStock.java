package tn.itbs.projet.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMouvement;

    @NotNull(message = "Le type de mouvement est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    @Positive(message = "La quantité doit être positive")
    private int quantite;

    private LocalDateTime date;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;

    @NotNull(message = "L'entrepôt est obligatoire")
    @ManyToOne
    @JoinColumn(name = "idEntrepot")
    private Entrepot entrepot;

    public enum TypeMouvement {
        ENTREE, SORTIE
    }
}