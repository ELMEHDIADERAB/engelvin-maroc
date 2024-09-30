package com.example.engelvinmaroc.DTO;

import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Commande;
import com.example.engelvinmaroc.entities.Fournisseur;
import com.example.engelvinmaroc.entities.RoleUtilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private List<RoleUtilisateur> roles;
    private List<Commande> commandes;
    private List<Article> articles;
    private List<Fournisseur> fournisseurs;
}
