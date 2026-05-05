package tn.itbs.projet.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMouvement;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;   // ENTREE ou SORTIE

    private int quantite;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idEntrepot")
    private Entrepot entrepot;

    public enum TypeMouvement {
        ENTREE, SORTIE
    }
}