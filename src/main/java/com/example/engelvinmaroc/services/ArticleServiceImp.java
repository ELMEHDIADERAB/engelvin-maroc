package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Fournisseur;
import com.example.engelvinmaroc.repositories.ArticleRepository;
import com.example.engelvinmaroc.repositories.Article_CommandeRepository;
import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp  implements ArticleService {
    private final ArticleRepository articleRepository;

    private Article_CommandeRepository article_commandeRepository;

    public ArticleServiceImp(ArticleRepository articleRepository, Article_CommandeRepository article_commandeRepository) {
        this.articleRepository = articleRepository;
        this.article_commandeRepository = article_commandeRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article Update(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);

    }


    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public List<Article> getAllArticles(Fournisseur fournisseur) {
        if(fournisseur == null){
            return articleRepository.findAll();
        }else {
            return articleRepository.findArticleByFournisseur(fournisseur);
        }

    }

    @Override
    public boolean existbyNomAndFournisseur(String title, Fournisseur fournisseur) {
        return articleRepository.existsByTitleAndFournisseur(title,fournisseur);
    }

    @Override
    public boolean isArticleInArticleCommande(Long id) {
        // Vérifie si l'article est utilisé dans une commande
        return article_commandeRepository.existsByArticle_Id(id);
    }

/*
    @Override
    public Page<Article> getAllArticlesByPage(int page, int size) {
        return articleRepository.findAll(PageRequest.of(page,size));
    }
*/


}
