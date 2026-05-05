package tn.itbs.projet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.Stock;
import tn.itbs.projet.repositories.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    public ResponseEntity<String> ajouterStock(Stock stock) {
        stockRepo.save(stock);
        return ResponseEntity.ok("Stock ajouté avec succès");
    }

    public List<Stock> getTousLesStocks() {
        return stockRepo.findAll();
    }

    public Stock trouverStockParId(int idStock) {
        return stockRepo.findById(idStock).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !")
        );
    }

    public List<Stock> getStocksParEntrepot(int idEntrepot) {
        return stockRepo.findByEntrepotIdEntrepot(idEntrepot);
    }

    public List<Stock> getStocksParProduit(int idProduit) {
        return stockRepo.findByProduitIdProduit(idProduit);
    }

    // ✅ Alerte automatique : retourne tous les stocks en dessous du seuil
    public List<Stock> getStocksEnAlerte() {
        List<Stock> tousLesStocks = stockRepo.findAll();
        return tousLesStocks.stream()
            .filter(s -> s.getQuantite() <= s.getSeuilAlerte())
            .toList();
    }

    // ✅ Vérification alerte pour un stock précis
    public ResponseEntity<String> verifierAlerte(int idStock) {
        Stock stock = trouverStockParId(idStock);
        if (stock.getQuantite() <= stock.getSeuilAlerte()) {
            return ResponseEntity.ok("⚠️ ALERTE : Stock bas pour le produit ["
                + stock.getProduit().getNom() + "] dans l'entrepôt ["
                + stock.getEntrepot().getNom() + "] — Quantité actuelle : "
                + stock.getQuantite());
        }
        return ResponseEntity.ok("✅ Stock suffisant");
    }

    public ResponseEntity<String> mettreAJourStock(int idStock, Stock stock) {
        stockRepo.findById(idStock).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !")
        );
        stock.setIdStock(idStock);
        stockRepo.save(stock);
        return ResponseEntity.ok("Stock mis à jour avec succès");
    }

    public ResponseEntity<String> supprimerStock(int idStock) {
        stockRepo.findById(idStock).ifPresentOrElse(
            s -> stockRepo.delete(s),
            () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !"); }
        );
        return ResponseEntity.ok("Stock supprimé avec succès");
    }
}