package tn.itbs.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.itbs.projet.entities.Utilisateur;
import tn.itbs.projet.entities.Utilisateur.Role;
import tn.itbs.projet.repositories.UtilisateurRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (utilisateurRepository.findByEmail("admin@stock.tn") == null) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setEmail("admin@stock.tn");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            utilisateurRepository.save(admin);
            System.out.println("✅ Admin créé : admin@stock.tn / admin123");
        }
    }
}