package tn.itbs.projet.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.MouvementStock;
import tn.itbs.projet.entities.MouvementStock.TypeMouvement;
import tn.itbs.projet.entities.Stock;
import tn.itbs.projet.repositories.EntrepotRepository;
import tn.itbs.projet.repositories.MouvementStockRepository;
import tn.itbs.projet.repositories.ProduitRepository;
import tn.itbs.projet.repositories.StockRepository;

@Service
public class MouvementStockService {

    @Autowired
    private MouvementStockRepository mouvementRepo;

    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private ProduitRepository produitRepo;

    @Autowired
    private EntrepotRepository entrepotRepo;

    public ResponseEntity<String> entreeStock(MouvementStock mouvement) {
        var produit = produitRepo.findById(mouvement.getProduit().getIdProduit())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        var entrepot = entrepotRepo.findById(mouvement.getEntrepot().getIdEntrepot())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrepôt non trouvé"));

        Stock stock = stockRepo.findByProduitIdProduitAndEntrepotIdEntrepot(
            produit.getIdProduit(), entrepot.getIdEntrepot()
        );

        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Aucun stock trouvé pour ce produit dans cet entrepôt !");
        }

        stock.setQuantite(stock.getQuantite() + mouvement.getQuantite());
        stockRepo.save(stock);

        mouvement.setProduit(produit);
        mouvement.setEntrepot(entrepot);
        mouvement.setType(TypeMouvement.ENTREE);
        mouvement.setDate(LocalDateTime.now());
        mouvementRepo.save(mouvement);

        return ResponseEntity.ok("Entrée enregistrée — Nouvelle quantité : " + stock.getQuantite());
    }

    public ResponseEntity<String> sortieStock(MouvementStock mouvement) {
        var produit = produitRepo.findById(mouvement.getProduit().getIdProduit())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        var entrepot = entrepotRepo.findById(mouvement.getEntrepot().getIdEntrepot())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrepôt non trouvé"));

        Stock stock = stockRepo.findByProduitIdProduitAndEntrepotIdEntrepot(
            produit.getIdProduit(), entrepot.getIdEntrepot()
        );

        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Aucun stock trouvé pour ce produit dans cet entrepôt !");
        }

        if (stock.getQuantite() < mouvement.getQuantite()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Stock insuffisant ! Disponible : " + stock.getQuantite());
        }

        stock.setQuantite(stock.getQuantite() - mouvement.getQuantite());
        stockRepo.save(stock);

        mouvement.setProduit(produit);
        mouvement.setEntrepot(entrepot);
        mouvement.setType(TypeMouvement.SORTIE);
        mouvement.setDate(LocalDateTime.now());
        mouvementRepo.save(mouvement);

        return ResponseEntity.ok("Sortie enregistrée — Nouvelle quantité : " + stock.getQuantite());
    }

    public List<MouvementStock> getTousLesMouvements() {
        return mouvementRepo.findAll();
    }

    public MouvementStock trouverMouvementParId(int idMouvement) {
        return mouvementRepo.findById(idMouvement).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé !")
        );
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
            () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé !"); }
        );
        return ResponseEntity.ok("Mouvement supprimé avec succès");
    }
}