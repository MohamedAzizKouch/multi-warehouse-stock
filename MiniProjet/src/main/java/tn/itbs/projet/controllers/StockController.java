package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.projet.entities.Stock;
import tn.itbs.projet.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterStock(@Valid @RequestBody Stock stock, BindingResult result) {
        return stockService.ajouterStock(stock, result);
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

    @GetMapping("/alertes")
    public List<Stock> getStocksEnAlerte() {
        return stockService.getStocksEnAlerte();
    }

    @GetMapping("/alerte/{idStock}")
    public ResponseEntity<String> verifierAlerte(@PathVariable int idStock) {
        return stockService.verifierAlerte(idStock);
    }

    @PutMapping("/update/{idStock}")
    public ResponseEntity<String> mettreAJourStock(@PathVariable int idStock,
                                                    @Valid @RequestBody Stock stock,
                                                    BindingResult result) {
        return stockService.mettreAJourStock(idStock, stock, result);
    }

    @DeleteMapping("/delete/{idStock}")
    public ResponseEntity<String> supprimerStock(@PathVariable int idStock) {
        return stockService.supprimerStock(idStock);
    }
}