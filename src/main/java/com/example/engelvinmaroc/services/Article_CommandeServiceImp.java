package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Commande;
import com.example.engelvinmaroc.entities.Fournisseur;
import com.example.engelvinmaroc.repositories.ArticleRepository;
import com.example.engelvinmaroc.repositories.Article_CommandeRepository;
import com.example.engelvinmaroc.repositories.CommandeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class Article_CommandeServiceImp implements Article_CommandeService {

    private final Article_CommandeRepository article_commandeRepository;
    private final ArticleRepository articleRepository;
    private CommandeService commandeService;

    @Override
    public Article_Commande ajouterCommande(Article_Commande article_commande) {
        Commande commande = new Commande();
        Date date = new Date();
        commande.setDate(date);
        commandeService.saveCommande(commande);
        article_commande.setCommande(commande);
        return article_commandeRepository.save(article_commande);
    }

    @Override
    public List<Object[]> getCommandes() {
        return article_commandeRepository.findMostOrderedProducts();
    }
    @Override
    public List<Object[]> findMostOrderedProducts() {
        return article_commandeRepository.findMostOrderedProducts();
    }


}

