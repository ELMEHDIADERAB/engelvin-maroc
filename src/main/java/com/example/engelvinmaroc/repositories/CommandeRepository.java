package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>{
    @Query("SELECT c.id, ac.quantite, c.date, a.title, a.id, ac.id, c.validation " +
            "FROM Article_Commande ac " +
            "JOIN ac.commande c " +
            "JOIN ac.article a " +
            "WHERE c.validation = false OR c.validation = true " +
            "GROUP BY c.id, ac.quantite, c.date, a.title, a.id, ac.id, c.validation " +
            "ORDER BY  c.date DESC")
    List<Object[]> findC();


//    @Query("SELECT c.id,ac.quantite, c.date, a.title , a.id,  ac.id,c.validation " +
//            "FROM Article_Commande ac " +
//            "JOIN ac.commande c " +
//            "JOIN ac.article a " +
//            "WHERE c.validation = false OR c.validation = true " +
//            "GROUP BY c.id, ac.quantite, c.date, a.title, a.id, ac.id, c.validation " +
//            "ORDER BY c.id desc")
//    List<Object[]> findC();



    @Query("SELECT f.adresse,f.nom,f.telephone, c.id,ac.quantite, c.date, a.title , a.id,  ac.id , a.prix_Unitaire_HT , a.unite,f.email,ac.commentaire,c.validation " +
            "FROM Article_Commande ac " +
            "JOIN ac.commande c " +
            "JOIN ac.article a " +
            "JOIN  a.fournisseur f " +
            "where c.id=:id " )
    List<Object[]> findCById(@Param("id")Long id);


    @Query("SELECT c FROM Commande c WHERE c.validation = true")
    List<Commande> findCommandesValidees();

    @Query("SELECT c FROM Commande c WHERE c.validation = false")
    List<Commande> findCommandesNonValidees();

}
