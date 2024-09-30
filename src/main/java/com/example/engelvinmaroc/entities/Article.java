package com.example.engelvinmaroc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Remplir le champ titre")
    private String title;
    @NotBlank(message = "Remplir le champ unite")
    private String unite;
    private Double prix_Unitaire_HT;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", unite='" + unite + '\'' +
                ", prix_Unitaire_HT=" + prix_Unitaire_HT +
                '}';
    }

    @ManyToOne
    private Fournisseur fournisseur;
    @ManyToOne
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY)
    private List<Article_Commande> Acommandes=new ArrayList<>();

}
