package tn.itbs.projet.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.projet.entities.MouvementStock;
import tn.itbs.projet.entities.MouvementStock.TypeMouvement;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Integer> {

    // Mouvements par type
    List<MouvementStock> findByType(TypeMouvement type);

    // Mouvements dun produit donne
    List<MouvementStock> findByProduitIdProduit(int idProduit);

    // Mouvements dun entrepot donnee
    List<MouvementStock> findByEntrepotIdEntrepot(int idEntrepot);

    // Mouvements entre deux dates
    List<MouvementStock> findByDateBetween(LocalDateTime debut, LocalDateTime fin);

    // Mouvements dun produit dans un entrepot precis
    List<MouvementStock> findByProduitIdProduitAndEntrepotIdEntrepot(int idProduit, int idEntrepot);
}