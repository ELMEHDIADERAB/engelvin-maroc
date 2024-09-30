package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Fournisseur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FournisseurService {
    Fournisseur save(Fournisseur fournisseur);
    Fournisseur Update(Fournisseur fournisseur);
    void deleteFournisseurById(Long id);
    Fournisseur getFournisseurById(Long id);
    List<Fournisseur> getAllFournisseurs();
    String findByNom(String nom);
    boolean existsByNomOrEmailOrTelephone(String nom, String email, String tel);
boolean existsByEmail(String email);
boolean existsByEmailAndNotId(String email, Long id);
Fournisseur findByEmail(String email);
void update (Fournisseur fournisseur);

    // String getFournisseurByNom(String nom);
}
