package com.example.engelvinmaroc.DTO;

import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Utilisateur;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommandeDTO {
    private Long id;
    private Date date;
    private Utilisateur utilisateur;
    private List<Article_Commande> Acommandes;
}
