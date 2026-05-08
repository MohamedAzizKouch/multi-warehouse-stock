package tn.itbs.projet.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.Stock;
import tn.itbs.projet.repositories.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    public ResponseEntity<String> ajouterStock(Stock stock, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        stockRepo.save(stock);
        return ResponseEntity.ok("Stock ajouté avec succès !");
    }

    public List<Stock> getTousLesStocks() {
        return stockRepo.findAll();
    }

    public Stock trouverStockParId(int idStock) {
        return stockRepo.findById(idStock)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !"));
    }

    public List<Stock> getStocksParEntrepot(int idEntrepot) {
        return stockRepo.findByEntrepotIdEntrepot(idEntrepot);
    }

    public List<Stock> getStocksParProduit(int idProduit) {
        return stockRepo.findByProduitIdProduit(idProduit);
    }

    public List<Stock> getStocksEnAlerte() {
        return stockRepo.findAll().stream()
            .filter(s -> s.getQuantite() < s.getSeuilAlerte())
            .collect(Collectors.toList());
    }

    public ResponseEntity<String> verifierAlerte(int idStock) {
        Stock stock = stockRepo.findById(idStock)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !"));
        if (stock.getQuantite() < stock.getSeuilAlerte()) {
            return ResponseEntity.ok("⚠️ ALERTE : Stock bas ! Quantité : " + stock.getQuantite());
        }
        return ResponseEntity.ok("✅ Stock suffisant. Quantité : " + stock.getQuantite());
    }

    public ResponseEntity<String> mettreAJourStock(int idStock, Stock nv, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        stockRepo.findById(idStock).ifPresentOrElse(
            s -> {
                s.setQuantite(nv.getQuantite());
                s.setSeuilAlerte(nv.getSeuilAlerte());
                s.setProduit(nv.getProduit());
                s.setEntrepot(nv.getEntrepot());
                stockRepo.save(s);
            },
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !");
            }
        );
        return ResponseEntity.ok("Stock mis à jour avec succès !");
    }

    public ResponseEntity<String> supprimerStock(int idStock) {
        stockRepo.findById(idStock).ifPresentOrElse(
            s -> stockRepo.delete(s),
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock non trouvé !");
            }
        );
        return ResponseEntity.ok("Stock supprimé avec succès !");
    }
}