package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UtilisateurService {
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Utilisateur update(Utilisateur utilisateur);
    void deleteUtilisateurById(Long id);
    Utilisateur getUtilisateurById(Long id);
    List<Utilisateur> findAll();
    Utilisateur loadUserByUsername(String email);
    void changePassword(String newPassword);
    Utilisateur updateProfile(String nom,String prenom,String email);
    Utilisateur updateUtilisateur(String nom,String prenom,String email, Long idUtilisateur);
    boolean existUtilisateur(String email, String nom, String prenom);


    void sendPasswordResetLink(String email);
  //  void generateResetToken(String email);
    void sendResetEmail(String email);
   // void sendResetEmail(String email, String token);
    //boolean validateResetToken(String token);
    void resetPassword(String email, String newPassword);
    //void resetPassword(String token, String newPassword);
}
