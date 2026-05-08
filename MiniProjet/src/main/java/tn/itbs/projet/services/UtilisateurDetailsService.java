package tn.itbs.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import tn.itbs.projet.entities.Utilisateur;
import tn.itbs.projet.repositories.UtilisateurRepository;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import java.util.List;

@Service
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur u = utilisateurRepository.findByEmail(email);
        if (u == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }
        return new User(
            u.getEmail(),
            u.getMotDePasse(),
            List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
        );
    }
}