package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.Article;
import com.example.engelvinmaroc.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticleByFournisseur(Fournisseur fournisseur);

    boolean existsByTitleAndFournisseur(String title, Fournisseur fournisseur);
}
