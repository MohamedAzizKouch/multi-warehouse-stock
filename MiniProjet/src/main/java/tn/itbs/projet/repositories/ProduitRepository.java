package tn.itbs.projet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.projet.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    List<Produit> findByCategorie(String categorie);

    List<Produit> findByNomContaining(String nom);

    List<Produit> findByPrixLessThan(double prix);

    List<Produit> findByFournisseur(String fournisseur);

    List<Produit> findByCategorieAndFournisseur(String categorie, String fournisseur);
}