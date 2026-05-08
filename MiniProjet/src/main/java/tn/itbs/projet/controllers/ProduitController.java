package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.projet.entities.Produit;
import tn.itbs.projet.services.ProduitService;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterProduit(@Valid @RequestBody Produit produit, BindingResult result) {
        return produitService.ajouterProduit(produit, result);
    }

    @GetMapping("/all")
    public List<Produit> getTousLesProduits() {
        return produitService.getTousLesProduits();
    }

    @GetMapping("/{idProduit}")
    public Produit trouverProduitParId(@PathVariable int idProduit) {
        return produitService.trouverProduitParId(idProduit);
    }

    @GetMapping("/categorie/{categorie}")
    public List<Produit> trouverParCategorie(@PathVariable String categorie) {
        return produitService.trouverParCategorie(categorie);
    }

    @GetMapping("/fournisseur/{fournisseur}")
    public List<Produit> trouverParFournisseur(@PathVariable String fournisseur) {
        return produitService.trouverParFournisseur(fournisseur);
    }

    @GetMapping("/recherche/{nom}")
    public List<Produit> rechercherParNom(@PathVariable String nom) {
        return produitService.rechercherParNom(nom);
    }

    @PutMapping("/update/{idProduit}")
    public ResponseEntity<String> mettreAJourProduit(@PathVariable int idProduit, 
                                                       @Valid @RequestBody Produit produit, 
                                                       BindingResult result) {
        return produitService.mettreAJourProduit(idProduit, produit, result);
    }

    @DeleteMapping("/delete/{idProduit}")
    public ResponseEntity<String> supprimerProduit(@PathVariable int idProduit) {
        return produitService.supprimerProduit(idProduit);
    }
}