package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.RoleUtilisateur;
import com.example.engelvinmaroc.entities.Utilisateur;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private UtilisateurService utilisateurService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurService.loadUserByUsername(email);
        if (utilisateur == null) {
            throw new UsernameNotFoundException(String.format("Utilisateur avec email %s introuvable", email));
        }

        // Force the initialization of the roles collection
        utilisateur.getRoles().size();

        String[] roles = utilisateur.getRoles().stream().map(RoleUtilisateur::getNom).toArray(String[]::new);
        return User
                .withUsername(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .roles(roles).build();
    }
}
