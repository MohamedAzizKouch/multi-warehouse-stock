package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.itbs.projet.entities.Stock;
import tn.itbs.projet.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterStock(@RequestBody Stock stock) {
        return stockService.ajouterStock(stock);
    }

    @GetMapping("/all")
    public List<Stock> getTousLesStocks() {
        return stockService.getTousLesStocks();
    }

    @GetMapping("/{idStock}")
    public Stock trouverStockParId(@PathVariable int idStock) {
        return stockService.trouverStockParId(idStock);
    }

    @GetMapping("/entrepot/{idEntrepot}")
    public List<Stock> getStocksParEntrepot(@PathVariable int idEntrepot) {
        return stockService.getStocksParEntrepot(idEntrepot);
    }

    @GetMapping("/produit/{idProduit}")
    public List<Stock> getStocksParProduit(@PathVariable int idProduit) {
        return stockService.getStocksParProduit(idProduit);
    }

    //  Endpoint alertes stock bas
    @GetMapping("/alertes")
    public List<Stock> getStocksEnAlerte() {
        return stockService.getStocksEnAlerte();
    }

    // Vérification alerte pour un stock précis
    @GetMapping("/alerte/{idStock}")
    public ResponseEntity<String> verifierAlerte(@PathVariable int idStock) {
        return stockService.verifierAlerte(idStock);
    }

    @PutMapping("/update/{idStock}")
    public ResponseEntity<String> mettreAJourStock(@PathVariable int idStock,
                                                    @RequestBody Stock stock) {
        return stockService.mettreAJourStock(idStock, stock);
    }

    @DeleteMapping("/delete/{idStock}")
    public ResponseEntity<String> supprimerStock(@PathVariable int idStock) {
        return stockService.supprimerStock(idStock);
    }
}