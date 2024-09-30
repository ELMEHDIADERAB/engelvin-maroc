package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    @Query("select f from Fournisseur f where f.nom = :nom")
    String findByNom(@Param("nom") String nom);
    boolean existsByEmail(String email);

    boolean existsByNomOrEmailOrTelephone(String nom, String email,String tel);

    Fournisseur findByEmail(String email);
}
