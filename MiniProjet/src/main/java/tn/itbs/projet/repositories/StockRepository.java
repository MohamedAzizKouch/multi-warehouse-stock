package tn.itbs.projet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.projet.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    // Tous les stocks dun entrepot donne
    List<Stock> findByEntrepotIdEntrepot(int idEntrepot);

    // Tous les stocks dun produit donne
    List<Stock> findByProduitIdProduit(int idProduit);

    // Stocks dont la quantité est inférieure au seuil d'alerte (stock bas)
    List<Stock> findByQuantiteLessThanEqual(int quantite);

    // Stocks critiques : quantité < seuilAlerte
    List<Stock> findByQuantiteLessThan(int seuilAlerte);

    // Stock d'un produit dans un entrepôt précis
    Stock findByProduitIdProduitAndEntrepotIdEntrepot(int idProduit, int idEntrepot);
}