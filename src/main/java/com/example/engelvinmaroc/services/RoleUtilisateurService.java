package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.RoleUtilisateur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleUtilisateurService {
    RoleUtilisateur addRoleUtilisateur(RoleUtilisateur roleUtilisateur);
    List<RoleUtilisateur> getAllRoleUtilisateur();

}
