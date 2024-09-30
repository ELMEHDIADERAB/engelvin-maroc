package com.example.engelvinmaroc.DTO;

import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Commande;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article_CommandeDTO {
    private long id;
    private int quantite;
    private double montant_HT;
    private Article article;
    private Commande commande;
}
