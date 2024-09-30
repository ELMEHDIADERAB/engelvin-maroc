package com.example.engelvinmaroc.DTO;

import com.example.engelvinmaroc.entities.Article;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FournisseurDTO {
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
    private Utilisateur utilisateur;
    private List<Article> articles;

}
