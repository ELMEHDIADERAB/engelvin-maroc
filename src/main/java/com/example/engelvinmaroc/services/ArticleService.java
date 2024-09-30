package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Fournisseur;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    Article save(Article article);
    Article Update(Article article);
    void deleteArticleById(Long id);
    Article getArticleById(Long id);
    List<Article> getAllArticles(Fournisseur fournisseur);
    boolean existbyNomAndFournisseur(String nom, Fournisseur fournisseur);
    //Page<Article> getAllArticlesByPage(int page, int size);

    boolean isArticleInArticleCommande(Long id);
}
