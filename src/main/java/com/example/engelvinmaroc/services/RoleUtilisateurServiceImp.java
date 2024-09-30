package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.RoleUtilisateur;
import com.example.engelvinmaroc.repositories.RoleUtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class    RoleUtilisateurServiceImp implements RoleUtilisateurService{
    private RoleUtilisateurRepository roleUtilisateurRepository;

    public RoleUtilisateurServiceImp(RoleUtilisateurRepository roleUtilisateurRepository) {
        this.roleUtilisateurRepository = roleUtilisateurRepository;
    }

    @Override
    public RoleUtilisateur addRoleUtilisateur(RoleUtilisateur roleUtilisateur) {
        return roleUtilisateurRepository.save(roleUtilisateur);
    }

    @Override
    public List<RoleUtilisateur> getAllRoleUtilisateur() {
        return roleUtilisateurRepository.findAll();
    }

}
