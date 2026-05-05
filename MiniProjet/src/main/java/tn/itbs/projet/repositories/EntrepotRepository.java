package tn.itbs.projet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.projet.entities.Entrepot;

public interface EntrepotRepository extends JpaRepository<Entrepot, Integer> {

    List<Entrepot> findByNomContaining(String nom);

    List<Entrepot> findByCapaciteGreaterThan(int capacite);

    List<Entrepot> findByAdresseContaining(String adresse);
}