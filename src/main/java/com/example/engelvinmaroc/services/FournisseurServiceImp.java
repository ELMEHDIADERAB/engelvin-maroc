package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Fournisseur;
import com.example.engelvinmaroc.repositories.FournisseurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FournisseurServiceImp implements FournisseurService {
    private FournisseurRepository fournisseurRepository;

    @Override
    public Fournisseur save(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur Update(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public void deleteFournisseurById(Long id) {
        fournisseurRepository.deleteById(id);
    }



    @Override
    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id).get();
    }

    @Override
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public String findByNom(String nom) {
        return fournisseurRepository.findByNom(nom);
    }

    @Override
    public boolean existsByNomOrEmailOrTelephone(String nom, String email, String tel) {
        return fournisseurRepository.existsByNomOrEmailOrTelephone(nom, email, tel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return fournisseurRepository.existsByEmail(email);
    }

//    public String getFournisseurByNom(String nom) {
//        return fournisseurRepository.findByNom(nom).toString();
//    }

    public Fournisseur findByEmail(String email) {
        return fournisseurRepository.findByEmail(email);
    }

    public boolean existsByEmailAndNotId(String email, Long id) {
        Fournisseur fournisseur = findByEmail(email);
        return fournisseur != null && !(fournisseur.getId() == id);
    }

    public void update(Fournisseur fournisseur) {
        fournisseurRepository.save(fournisseur);
    }

}
