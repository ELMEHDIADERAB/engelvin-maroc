package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Commande;
import com.example.engelvinmaroc.entities.Fournisseur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Article_CommandeService {
    Article_Commande ajouterCommande(Article_Commande article_commande);
    List<Object[]> getCommandes();
   List<Object[]> findMostOrderedProducts() ;

}
