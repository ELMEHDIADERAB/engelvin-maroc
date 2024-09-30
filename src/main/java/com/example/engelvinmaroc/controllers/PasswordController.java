package com.example.engelvinmaroc.controllers;

import com.example.engelvinmaroc.entities.Utilisateur;
import com.example.engelvinmaroc.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class PasswordController {
//
//    @Autowired
//    private UtilisateurService utilisateurService;
//
//    @RequestMapping("/pwd")
//    public String pwd() {
//        return "forgot-password";
//    }
//
//    @PostMapping("/forgot-password")
//    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
//        // Vérification si un compte avec cet email existe
//        Utilisateur utilisateur = utilisateurService.loadUserByUsername(email);
//        if (utilisateur == null) {
//            // Si l'utilisateur n'existe pas, on ajoute un message d'erreur
//            redirectAttributes.addFlashAttribute("errorMessage", "Aucun compte trouvé avec cet email.");
//            return "redirect:/pwd";
//        }
//
//        // Si l'utilisateur existe, on génère le token de réinitialisation
//        utilisateurService.generateResetToken(email);
////        redirectAttributes.addFlashAttribute("message", "Un email a été envoyé pour réinitialiser votre mot de passe.");
//        redirectAttributes.addFlashAttribute("message", "Nous avons envoyé un e-mail à "+email +" avec un lien pour récupérer votre compte.");
//        return "redirect:/pwd";
//    }
//
////    @PostMapping("/forgot-password")
////    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
////        utilisateurService.generateResetToken(email);
////        redirectAttributes.addFlashAttribute("message", "Un email a été envoyé pour réinitialiser votre mot de passe.");
////        return "redirect:/pwd";
////    }
//
//    @GetMapping("/reset-password")
//    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
//        boolean valid = utilisateurService.validateResetToken(token);
//        if (!valid) {
//            return "redirect:/error"; // Redirigez vers une page d'erreur si le jeton est invalide
//        }
//        model.addAttribute("token", token);
////        return "redirect:/reset-password";
//        return "reset-password";
//    }
//
//    @PostMapping("/resetpassword")
//    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
//        try {
//            utilisateurService.resetPassword(token, password);
//            redirectAttributes.addFlashAttribute("message", "Votre mot de passe a été réinitialisé avec succès.");
//            return "redirect:/login";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Impossible de réinitialiser le mot de passe. Le token est peut-être invalide ou expiré.");
//            return "redirect:/reset-password?token=" + token;
//        }
//    }
//}

@Controller
public class PasswordController {

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping("/pwd")
    public String pwd() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = utilisateurService.loadUserByUsername(email);
        if (utilisateur == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Aucun compte trouvé avec cet email.");
            return "redirect:/pwd";
        }

        utilisateurService.sendPasswordResetLink(email);
        redirectAttributes.addFlashAttribute("message", "Nous avons envoyé un e-mail à " + email + " avec un lien pour récupérer votre compte.");
        return "redirect:/pwd";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("email") String email, Model model) {
        Utilisateur utilisateur = utilisateurService.loadUserByUsername(email);
        if (utilisateur == null) {
            return "redirect:/error"; // Redirigez vers une page d'erreur si l'utilisateur est introuvable
        }
        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.resetPassword(email, password);
            redirectAttributes.addFlashAttribute("message", "Votre mot de passe a été réinitialisé avec succès.");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace(); // Pour capturer et afficher l'erreur si elle se produit
            redirectAttributes.addFlashAttribute("error", "Impossible de réinitialiser le mot de passe.");
            return "redirect:/reset-password?email=" + email;
        }
    }

}
