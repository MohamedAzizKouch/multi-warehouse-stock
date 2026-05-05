package tn.itbs.projet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.itbs.projet.entities.Utilisateur;
import tn.itbs.projet.entities.Utilisateur.Role;
import tn.itbs.projet.services.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/add")
    public ResponseEntity<String> ajouterUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.ajouterUtilisateur(utilisateur);
    }

    @GetMapping("/all")
    public List<Utilisateur> getTousLesUtilisateurs() {
        return utilisateurService.getTousLesUtilisateurs();
    }

    @GetMapping("/{idUtilisateur}")
    public Utilisateur trouverUtilisateurParId(@PathVariable int idUtilisateur) {
        return utilisateurService.trouverUtilisateurParId(idUtilisateur);
    }

    @GetMapping("/email/{email}")
    public Utilisateur trouverParEmail(@PathVariable String email) {
        return utilisateurService.trouverParEmail(email);
    }

    @GetMapping("/role/{role}")
    public List<Utilisateur> trouverParRole(@PathVariable Role role) {
        return utilisateurService.trouverParRole(role);
    }

    @PutMapping("/update/{idUtilisateur}")
    public ResponseEntity<String> mettreAJourUtilisateur(@PathVariable int idUtilisateur,
                                                          @RequestBody Utilisateur utilisateur) {
        return utilisateurService.mettreAJourUtilisateur(idUtilisateur, utilisateur);
    }

    @DeleteMapping("/delete/{idUtilisateur}")
    public ResponseEntity<String> supprimerUtilisateur(@PathVariable int idUtilisateur) {
        return utilisateurService.supprimerUtilisateur(idUtilisateur);
    }
}