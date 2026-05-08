package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.projet.entities.Entrepot;
import tn.itbs.projet.services.EntrepotService;

@RestController
@RequestMapping("/entrepot")
public class EntrepotController {

    @Autowired
    private EntrepotService entrepotService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterEntrepot(@Valid @RequestBody Entrepot entrepot, BindingResult result) {
        return entrepotService.ajouterEntrepot(entrepot, result);
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
                                                       @Valid @RequestBody Entrepot entrepot,
                                                       BindingResult result) {
        return entrepotService.mettreAJourEntrepot(idEntrepot, entrepot, result);
    }

    @DeleteMapping("/delete/{idEntrepot}")
    public ResponseEntity<String> supprimerEntrepot(@PathVariable int idEntrepot) {
        return entrepotService.supprimerEntrepot(idEntrepot);
    }
}