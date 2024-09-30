package com.example.engelvinmaroc.services;

import com.example.engelvinmaroc.entities.Utilisateur;
import com.example.engelvinmaroc.repositories.UtilisateurRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurServiceImp implements UtilisateurService {

    @Autowired
    private JavaMailSender mailSender;


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImp(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        //Utilisateur utilisateurExist = utilisateurRepository.findByEmail(utilisateur.getEmail());
        //if (utilisateurExist != null) throw new RuntimeException("Utilisateur already exist");
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateurById(Long id) {
    utilisateurRepository.deleteById(id);
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return  utilisateurRepository.findById(id).get();
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur loadUserByUsername(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

    @Override
    public void changePassword(String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(userName);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateProfile(String nom, String prenom, String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(userName);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(String nom, String prenom, String email,Long idUtilisateur) {
        Utilisateur user = utilisateurRepository.findUtilisateurById(idUtilisateur);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        return utilisateurRepository.save(user);
    }

    @Override
    public boolean existUtilisateur(String email, String nom, String prenom) {
        return utilisateurRepository.existsByEmailAndNomAndPrenom(email,nom,prenom);
    }





//    public void generateResetToken(String email) {
//        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
//        if (utilisateur != null) {
//            String token = UUID.randomUUID().toString();
//            utilisateur.setResetToken(token);
//            utilisateurRepository.save(utilisateur);
//
//            // Envoyer l'email avec le lien de réinitialisation
//            sendResetEmail(utilisateur.getEmail(), token);
//        }
//    }


public void sendPasswordResetLink(String email) {
    Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
    if (utilisateur != null) {
        // Envoyer l'email avec le lien de réinitialisation
        sendResetEmail(utilisateur.getEmail());
    }
}
    public void sendResetEmail(String email) {
        String subject = "Réinitialisation de mot de passe";
        String resetUrl = "http://localhost:8081/reset-password?email=" + email;
        String logoUrl = "https://drive.google.com/uc?export=view&id=1WZvdfW_nk5Zl6xWL0jWid-segveIyuF2";

        String message = "<div style='font-family:Arial,sans-serif;color:#333;width:100%;max-width:600px;margin:0 auto;padding:20px;background-color:#f9f9f9;border-radius:8px;'>"
                + "<div style='text-align:center;'>"
                + "<img src='" + logoUrl + "' class='img-fluid rounded-normal' alt='logo' style='width:150px;margin-bottom:20px;' />"
                + "</div>"
                + "<h2 style='color:#333;text-align:center;'>Réinitialiser votre mot de passe ?</h2>"
                + "<p style='color:#555;text-align:center;'>Si vous avez demandé une réinitialisation de mot de passe pour votre compte, cliquez sur le bouton ci-dessous pour compléter le processus.</p>"
                + "<p style='color:#555;text-align:center;'>Si vous n'avez pas fait cette demande, ignorez cet email.</p>"
                + "<div style='text-align:center;margin-top:20px;'>"
                + "<a href='" + resetUrl + "' style='display:inline-block;background-color:#1c87c9;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>Réinitialiser le mot de passe</a>"
                + "</div>"
                + "<p style='text-align:center;margin-top:20px;color:#999;font-size:12px;'>Cet email a été envoyé à <strong>" + email + "</strong>.</p>"
                + "<p style='text-align:center;color:#999;font-size:12px;'><b>ENGELVIN MAROC</b>, SIEGE SOCIAL: 109 ROUTE BOUSKOURA DRABNA SIDI MAAROUF, CASABLANCA</p>"
                + "</div>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true); // Le deuxième paramètre 'true' indique que le contenu est en HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        if (utilisateur != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            System.out.println("Nouveau mot de passe encodé : " + encodedPassword); // Vérifiez l'encodage
            utilisateur.setPassword(encodedPassword);
            utilisateurRepository.save(utilisateur);
            System.out.println("Mot de passe réinitialisé pour l'utilisateur : " + email);
        } else {
            System.out.println("Utilisateur non trouvé pour l'email donné.");
        }
    }

//    public void sendResetEmail(String email, String token) {
//        String subject = "Réinitialisation de mot de passe";
//        //String resetUrl = "http://localhost:8081/reset-password?token=" + token;
//        String resetUrl = "http://localhost:8081/reset-password";
//      //  String logoUrl = "../assets/images/unnamed.png"; // Remplacez par l'URL de votre logo
//        String logoUrl = "https://drive.google.com/uc?export=view&id=1WZvdfW_nk5Zl6xWL0jWid-segveIyuF2";
//
//
//        // Contenu HTML de l'email
//        String message = "<div style='font-family:Arial,sans-serif;color:#333;width:100%;max-width:600px;margin:0 auto;padding:20px;background-color:#f9f9f9;border-radius:8px;'>"
//                + "<div style='text-align:center;'>"
//                + "<img src='" + logoUrl + "' class='img-fluid rounded-normal' alt='logo' style='width:150px;margin-bottom:20px;' />"
//                + "</div>"
//                + "<h2 style='color:#333;text-align:center;'>Réinitialiser votre mot de passe ?</h2>"
//                + "<p style='color:#555;text-align:center;'>Si vous avez demandé une réinitialisation de mot de passe pour votre compte, cliquez sur le bouton ci-dessous pour compléter le processus.</p>"
//                + "<p style='color:#555;text-align:center;'>Si vous n'avez pas fait cette demande, ignorez cet email.</p>"
//                + "<div style='text-align:center;margin-top:20px;'>"
//                + "<a href='" + resetUrl + "' style='display:inline-block;background-color:#1c87c9;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>Réinitialiser le mot de passe</a>"
//                + "</div>"
//                + "<p style='text-align:center;margin-top:20px;color:#999;font-size:12px;'>Cet email a été envoyé à <strong>" + email + "</strong>.</p>"
//                + "<p style='text-align:center;color:#999;font-size:12px;'><b>ENGELVIN MAROC</b>, SIEGE SOCIAL: 109 ROUTE BOUSKOURA DRABNA SIDI MAAROUF, CASABLANCA</p>"
//                + "</div>";
//
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//
//            helper.setTo(email);
//            helper.setSubject(subject);
//            helper.setText(message, true); // Le deuxième paramètre 'true' indique que le contenu est en HTML
//
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            // Gestion des exceptions
//            e.printStackTrace();
//        }
//    }
//
//
//    public boolean validateResetToken(String token) {
//        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByResetToken(token);
//        return utilisateurOpt.isPresent();
//    }
//
//
//
//    @Override
//public void resetPassword(String token, String newPassword) {
//    Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByResetToken(token);
//    if (utilisateurOpt.isPresent()) {
//        Utilisateur utilisateur = utilisateurOpt.get();
//
//        // Hachage du mot de passe avant de le sauvegarder
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        utilisateur.setPassword(encodedPassword);
//
//        utilisateur.setResetToken(null); // Réinitialiser le jeton après utilisation
//        utilisateurRepository.save(utilisateur);
//    } else {
//        // Ajoutez un log ou un message d'erreur si l'utilisateur n'est pas trouvé
//        System.out.println("Utilisateur non trouvé pour le token donné.");
//    }
//    }
}
