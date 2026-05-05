package tn.itbs.projet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.Entrepot;
import tn.itbs.projet.repositories.EntrepotRepository;

@Service
public class EntrepotService {

    @Autowired
    private EntrepotRepository entrepotRepo;

    public ResponseEntity<String> ajouterEntrepot(Entrepot entrepot) {
        entrepotRepo.save(entrepot);
        return ResponseEntity.ok("Entrepôt ajouté avec succès");
    }

    public List<Entrepot> getTousLesEntrepots() {
        return entrepotRepo.findAll();
    }

    public Entrepot trouverEntrepotParId(int idEntrepot) {
        return entrepotRepo.findById(idEntrepot).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrepôt non trouvé !")
        );
    }

    public List<Entrepot> trouverParCapacite(int capacite) {
        return entrepotRepo.findByCapaciteGreaterThan(capacite);
    }

    public ResponseEntity<String> mettreAJourEntrepot(int idEntrepot, Entrepot entrepot) {
        entrepotRepo.findById(idEntrepot).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrepôt non trouvé !")
        );
        entrepot.setIdEntrepot(idEntrepot);
        entrepotRepo.save(entrepot);
        return ResponseEntity.ok("Entrepôt mis à jour avec succès");
    }

    public ResponseEntity<String> supprimerEntrepot(int idEntrepot) {
        entrepotRepo.findById(idEntrepot).ifPresentOrElse(
            e -> entrepotRepo.delete(e),
            () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrepôt non trouvé !"); }
        );
        return ResponseEntity.ok("Entrepôt supprimé avec succès");
    }
}