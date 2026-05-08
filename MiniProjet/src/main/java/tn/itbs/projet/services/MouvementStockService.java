package tn.itbs.projet.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.MouvementStock;
import tn.itbs.projet.entities.MouvementStock.TypeMouvement;
import tn.itbs.projet.repositories.MouvementStockRepository;
import tn.itbs.projet.repositories.StockRepository;

@Service
public class MouvementStockService {

    @Autowired
    private MouvementStockRepository mouvementRepo;

    @Autowired
    private StockRepository stockRepo;

    public ResponseEntity<String> entreeStock(MouvementStock mouvement, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        mouvement.setType(TypeMouvement.ENTREE);
        mouvement.setDate(LocalDateTime.now());
        mouvementRepo.save(mouvement);

        // Mettre à jour le stock
        stockRepo.findByProduitIdProduitAndEntrepotIdEntrepot(
            mouvement.getProduit().getIdProduit(),
            mouvement.getEntrepot().getIdEntrepot()
        );

        return ResponseEntity.ok("Entrée de stock enregistrée !");
    }

    public ResponseEntity<String> sortieStock(MouvementStock mouvement, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        mouvement.setType(TypeMouvement.SORTIE);
        mouvement.setDate(LocalDateTime.now());
        mouvementRepo.save(mouvement);
        return ResponseEntity.ok("Sortie de stock enregistrée !");
    }

    public List<MouvementStock> getTousLesMouvements() {
        return mouvementRepo.findAll();
    }

    public MouvementStock trouverMouvementParId(int idMouvement) {
        return mouvementRepo.findById(idMouvement)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé !"));
    }

    public List<MouvementStock> getMouvementsParType(TypeMouvement type) {
        return mouvementRepo.findByType(type);
    }

    public List<MouvementStock> getMouvementsParProduit(int idProduit) {
        return mouvementRepo.findByProduitIdProduit(idProduit);
    }

    public List<MouvementStock> getMouvementsParEntrepot(int idEntrepot) {
        return mouvementRepo.findByEntrepotIdEntrepot(idEntrepot);
    }

    public List<MouvementStock> getMouvementsEntreDates(LocalDateTime debut, LocalDateTime fin) {
        return mouvementRepo.findByDateBetween(debut, fin);
    }

    public ResponseEntity<String> supprimerMouvement(int idMouvement) {
        mouvementRepo.findById(idMouvement).ifPresentOrElse(
            m -> mouvementRepo.delete(m),
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé !");
            }
        );
        return ResponseEntity.ok("Mouvement supprimé avec succès !");
    }
}