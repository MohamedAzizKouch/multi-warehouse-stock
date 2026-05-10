package tn.itbs.projet.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.itbs.projet.entities.MouvementStock;
import tn.itbs.projet.entities.MouvementStock.TypeMouvement;
import tn.itbs.projet.services.MouvementStockService;

@RestController
@RequestMapping("/mouvement")
public class MouvementStockController {

    @Autowired
    private MouvementStockService mouvementService;

    @PostMapping("/entree")
    public ResponseEntity<String> entreeStock(@RequestBody MouvementStock mouvement) {
        return mouvementService.entreeStock(mouvement);
    }

    @PostMapping("/sortie")
    public ResponseEntity<String> sortieStock(@RequestBody MouvementStock mouvement) {
        return mouvementService.sortieStock(mouvement);
    }

    @GetMapping("/all")
    public List<MouvementStock> getTousLesMouvements() {
        return mouvementService.getTousLesMouvements();
    }

    @GetMapping("/{idMouvement}")
    public MouvementStock trouverMouvementParId(@PathVariable int idMouvement) {
        return mouvementService.trouverMouvementParId(idMouvement);
    }

    @GetMapping("/type/{type}")
    public List<MouvementStock> getMouvementsParType(@PathVariable TypeMouvement type) {
        return mouvementService.getMouvementsParType(type);
    }

    @GetMapping("/produit/{idProduit}")
    public List<MouvementStock> getMouvementsParProduit(@PathVariable int idProduit) {
        return mouvementService.getMouvementsParProduit(idProduit);
    }

    @GetMapping("/entrepot/{idEntrepot}")
    public List<MouvementStock> getMouvementsParEntrepot(@PathVariable int idEntrepot) {
        return mouvementService.getMouvementsParEntrepot(idEntrepot);
    }

    @GetMapping("/periode")
    public List<MouvementStock> getMouvementsEntreDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return mouvementService.getMouvementsEntreDates(debut, fin);
    }

    @DeleteMapping("/delete/{idMouvement}")
    public ResponseEntity<String> supprimerMouvement(@PathVariable int idMouvement) {
        return mouvementService.supprimerMouvement(idMouvement);
    }
}