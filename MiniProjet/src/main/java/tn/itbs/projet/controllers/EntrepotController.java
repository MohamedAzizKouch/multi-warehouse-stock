package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.itbs.projet.entities.Entrepot;
import tn.itbs.projet.services.EntrepotService;

@RestController
@RequestMapping("/entrepot")
public class EntrepotController {

    @Autowired
    private EntrepotService entrepotService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterEntrepot(@RequestBody Entrepot entrepot) {
        return entrepotService.ajouterEntrepot(entrepot);
    }

    @GetMapping("/all")
    public List<Entrepot> getTousLesEntrepots() {
        return entrepotService.getTousLesEntrepots();
    }

    @GetMapping("/{idEntrepot}")
    public Entrepot trouverEntrepotParId(@PathVariable int idEntrepot) {
        return entrepotService.trouverEntrepotParId(idEntrepot);
    }

    @GetMapping("/capacite/{capacite}")
    public List<Entrepot> trouverParCapacite(@PathVariable int capacite) {
        return entrepotService.trouverParCapacite(capacite);
    }

    @PutMapping("/update/{idEntrepot}")
    public ResponseEntity<String> mettreAJourEntrepot(@PathVariable int idEntrepot,
                                                       @RequestBody Entrepot entrepot) {
        return entrepotService.mettreAJourEntrepot(idEntrepot, entrepot);
    }

    @DeleteMapping("/delete/{idEntrepot}")
    public ResponseEntity<String> supprimerEntrepot(@PathVariable int idEntrepot) {
        return entrepotService.supprimerEntrepot(idEntrepot);
    }
}