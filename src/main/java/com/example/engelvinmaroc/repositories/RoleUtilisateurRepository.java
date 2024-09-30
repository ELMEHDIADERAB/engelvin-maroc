package com.example.engelvinmaroc.repositories;

import com.example.engelvinmaroc.entities.RoleUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUtilisateurRepository extends JpaRepository<RoleUtilisateur, Long> {
}
