package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Commande;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CommandeService {

    List<Object[]> getAllCommandesT();
    List<Object[]> getCommandeByID(Long id);
    Commande saveCommande(Commande commande);
    void deleteCommandeById(Long id);
    Commande ValiderCommande(Long id);
    Commande getCommandeById(Long id);

    List<Commande> getAllCommandes();
    List<Commande> getCommandeValide();
    List<Commande> getCommandeNonValide();


    Map<Integer, Map<Integer, Long>> getCommandesParMois();




}
