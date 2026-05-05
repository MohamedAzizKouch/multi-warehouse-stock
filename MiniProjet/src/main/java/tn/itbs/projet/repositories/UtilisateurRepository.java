package tn.itbs.projet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.projet.entities.Utilisateur;
import tn.itbs.projet.entities.Utilisateur.Role;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Recherche par email (utilise pour l'authentification Spring Security)
    Optional<Utilisateur> findByEmail(String email);

    // User par role
    List<Utilisateur> findByRole(Role role);

    // Recherche par nom
    List<Utilisateur> findByNomContaining(String nom);
}