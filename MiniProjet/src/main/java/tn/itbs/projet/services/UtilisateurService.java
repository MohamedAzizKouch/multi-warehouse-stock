package tn.itbs.projet.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.projet.entities.Utilisateur;
import tn.itbs.projet.entities.Utilisateur.Role;
import tn.itbs.projet.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    public ResponseEntity<String> ajouterUtilisateur(Utilisateur utilisateur, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        utilisateurRepo.save(utilisateur);
        return ResponseEntity.ok("Utilisateur ajouté avec succès !");
    }

    public List<Utilisateur> getTousLesUtilisateurs() {
        return utilisateurRepo.findAll();
    }

    public Utilisateur trouverUtilisateurParId(int idUtilisateur) {
        return utilisateurRepo.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé !"));
    }

    public Utilisateur trouverParEmail(String email) {
        Utilisateur u = utilisateurRepo.findByEmail(email);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé !");
        }
        return u;
    }

    public List<Utilisateur> trouverParRole(Role role) {
        return utilisateurRepo.findByRole(role);
    }

    public ResponseEntity<String> mettreAJourUtilisateur(int idUtilisateur, Utilisateur nv, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(erreurs);
        }
        utilisateurRepo.findById(idUtilisateur).ifPresentOrElse(
            u -> {
                u.setNom(nv.getNom());
                u.setEmail(nv.getEmail());
                u.setMotDePasse(nv.getMotDePasse());
                u.setRole(nv.getRole());
                utilisateurRepo.save(u);
            },
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé !");
            }
        );
        return ResponseEntity.ok("Utilisateur mis à jour avec succès !");
    }

    public ResponseEntity<String> supprimerUtilisateur(int idUtilisateur) {
        utilisateurRepo.findById(idUtilisateur).ifPresentOrElse(
            u -> utilisateurRepo.delete(u),
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé !");
            }
        );
        return ResponseEntity.ok("Utilisateur supprimé avec succès !");
    }
}