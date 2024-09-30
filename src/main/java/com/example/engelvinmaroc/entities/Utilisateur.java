package com.example.engelvinmaroc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Remplir champ nom")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Le nom ne doit contenir que des lettres et des espaces.")
    private String nom;
    @NotBlank(message = "Remplir champ prenom")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Le prénom ne doit contenir que des lettres et des espaces.")
    private String prenom;
    @NotBlank(message = "Remplir champ email")
    @Email(message = "Format de l'email invalide")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Le nom d'utilisateur de l'email ne doit pas contenir seulement des chiffres.")
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
   // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleUtilisateur> roles;

    @OneToMany(mappedBy = "utilisateur",fetch = FetchType.LAZY)
    private List<Commande> commandes=new ArrayList<>();

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @OneToMany(mappedBy = "utilisateur",fetch = FetchType.LAZY)
    private List<Article> articles=new ArrayList<>();
    @OneToMany(mappedBy = "utilisateur",fetch = FetchType.LAZY)
    private  List<Fournisseur> fournisseurs=new ArrayList<>();

    private String resetToken;  // Champ pour le jeton de réinitialisation
}
