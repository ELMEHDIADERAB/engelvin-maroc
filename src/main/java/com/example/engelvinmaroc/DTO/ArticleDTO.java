package com.example.engelvinmaroc.DTO;

import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Fournisseur;
import com.example.engelvinmaroc.entities.Utilisateur;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ArticleDTO {
    private long id;
    private String title;
    private String unite;
    private Double prix_Unitaire_HT;
    private Fournisseur Fournisseur;
    private Utilisateur utilisateur;
    private List<Article_Commande> Acommandes;
}
