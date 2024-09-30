package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findUtilisateurByEmail(String email);
    Utilisateur findUtilisateurById(Long id);
    boolean existsByEmailAndNomAndPrenom(String email, String nom, String prenom);

    Optional<Utilisateur> findByResetToken(String resetToken);
}
