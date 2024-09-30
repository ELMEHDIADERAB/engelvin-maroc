package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.Article_Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Article_CommandeRepository extends JpaRepository<Article_Commande, Long> {
    boolean existsByArticle_Id(Long id);

    @Query("SELECT a.title, SUM(ac.quantite) AS quantiteTotale, COUNT(ac.commande.id) AS nombreCommandes " +
            "FROM Article_Commande ac " +
            "JOIN ac.article a " +
            "GROUP BY a.title " +
            "ORDER BY quantiteTotale DESC")
    List<Object[]> findMostOrderedProducts();


}
