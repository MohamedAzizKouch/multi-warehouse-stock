package tn.itbs.projet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tn.itbs.projet.services.UtilisateurDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UtilisateurDetailsService utilisateurDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .userDetailsService(utilisateurDetailsService)
            .authorizeHttpRequests(auth -> auth
            		// ✅ Swagger accessible sans auth
            	    .requestMatchers(
            	        "/swagger-ui/**",
            	        "/swagger-ui.html",
            	        "/v3/api-docs/**"
            	    ).permitAll()
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/utilisateur/**").hasRole("ADMIN")
                .requestMatchers("/produit/add", "/produit/update/**", "/produit/delete/**")
                    .hasAnyRole("ADMIN", "GESTIONNAIRE")
                .requestMatchers("/entrepot/add", "/entrepot/update/**", "/entrepot/delete/**")
                    .hasAnyRole("ADMIN", "GESTIONNAIRE")
                .requestMatchers("/stock/add", "/stock/update/**", "/stock/delete/**")
                    .hasAnyRole("ADMIN", "GESTIONNAIRE")
                .requestMatchers("/mouvement/entree", "/mouvement/sortie")
                    .hasAnyRole("ADMIN", "GESTIONNAIRE")
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {});

        return http.build();
    }
}