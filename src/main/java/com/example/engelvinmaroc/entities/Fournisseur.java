package com.example.engelvinmaroc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Remplir champ nom")
    private String nom;
    @NotBlank(message = "Remplir champ email")
    private String email;
    private String adresse;
    private String telephone;

    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    @ManyToOne
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "fournisseur",fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

}
