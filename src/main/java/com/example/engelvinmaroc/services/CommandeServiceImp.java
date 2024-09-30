package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Commande;
import com.example.engelvinmaroc.repositories.CommandeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImp implements CommandeService {
    private CommandeRepository commandeRepository;


    public CommandeServiceImp(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public List<Object[]> getAllCommandesT() {
        return commandeRepository.findC();
    }

    @Override
    public List<Object[]> getCommandeByID(Long id) {
        return commandeRepository.findCById(id);
    }

    @Override
    public Commande saveCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public void deleteCommandeById(Long id) {
         commandeRepository.deleteById(id);
    }

    @Override
    public Commande ValiderCommande(Long id) {
      Commande commande1=  commandeRepository.findById(id).get();
      commande1.setValidation(true);
      return commandeRepository.save(commande1);
    }

    @Override
    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).get();
    }
    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public List<Commande> getCommandeValide() {
        return commandeRepository.findCommandesValidees();
    }

    @Override
    public List<Commande> getCommandeNonValide() {
        return commandeRepository.findCommandesNonValidees();
    }

//    public Map<Integer, Long> getCommandesParMois() {
//        List<Commande> commandes = commandeRepository.findAll();
//        return commandes.stream()
//                .collect(Collectors.groupingBy(
//                        commande -> commande.getDate().getMonth() + 1,
//                        Collectors.counting()
//                ));
//    }
public Map<Integer, Map<Integer, Long>> getCommandesParMois() {
    List<Commande> commandes = commandeRepository.findAll();

    return commandes.stream()
            .collect(Collectors.groupingBy(
                    commande -> {
                        LocalDate date = commande.getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return date.getYear();
                    }, // Regroupe par année
                    Collectors.groupingBy(
                            commande -> {
                                LocalDate date = commande.getDate().toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();
                                return date.getMonthValue();
                            }, // Regroupe par mois
                            Collectors.counting() // Compte le nombre de commandes
                    )
            ));
}



}
