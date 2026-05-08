package tn.itbs.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.itbs.projet.entities.MouvementStock.TypeMouvement;
import tn.itbs.projet.repositories.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MouvementStockRepository mouvementRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Map<String, Object> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalProduits", produitRepository.count());
        stats.put("totalEntrepots", entrepotRepository.count());
        stats.put("totalStocks", stockRepository.count());
        stats.put("totalMouvements", mouvementRepository.count());
        stats.put("totalUtilisateurs", utilisateurRepository.count());

        long entrees = mouvementRepository.findByType(TypeMouvement.ENTREE).size();
        long sorties = mouvementRepository.findByType(TypeMouvement.SORTIE).size();
        stats.put("totalEntrees", entrees);
        stats.put("totalSorties", sorties);

        long stocksEnAlerte = stockRepository.findAll().stream()
            .filter(s -> s.getQuantite() < s.getSeuilAlerte())
            .count();
        stats.put("stocksEnAlerte", stocksEnAlerte);

        return stats;
    }
}