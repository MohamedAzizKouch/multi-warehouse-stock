package tn.itbs.projet.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.Produit;
import tn.itbs.projet.repositories.ProduitRepository;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepo;

    public ResponseEntity<String> ajouterProduit(Produit produit, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        produitRepo.save(produit);
        return ResponseEntity.ok("Produit ajouté avec succès !");
    }

    public List<Produit> getTousLesProduits() {
        return produitRepo.findAll();
    }

    public Produit trouverProduitParId(int idProduit) {
        return produitRepo.findById(idProduit).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé !")
        );
    }

    public List<Produit> trouverParCategorie(String categorie) {
        return produitRepo.findByCategorie(categorie);
    }

    public List<Produit> trouverParFournisseur(String fournisseur) {
        return produitRepo.findByFournisseur(fournisseur);
    }

    public List<Produit> rechercherParNom(String nom) {
        return produitRepo.findByNomContaining(nom);
    }

    public ResponseEntity<String> mettreAJourProduit(int idProduit, Produit nv, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        produitRepo.findById(idProduit).ifPresentOrElse(
            p -> {
                p.setNom(nv.getNom());
                p.setCategorie(nv.getCategorie());
                p.setPrix(nv.getPrix());
                p.setFournisseur(nv.getFournisseur());
                p.setSeuilMin(nv.getSeuilMin());
                produitRepo.save(p);
            },
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé !");
            }
        );
        return ResponseEntity.ok("Produit mis à jour avec succès !");
    }

    public ResponseEntity<String> supprimerProduit(int idProduit) {
        produitRepo.findById(idProduit).ifPresentOrElse(
            p -> produitRepo.delete(p),
            () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé !"); }
        );
        return ResponseEntity.ok("Produit supprimé avec succès");
    }
}