//package com.example.engelvinmaroc.controllers;
//
//import com.example.engelvinmaroc.services.UtilisateurService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class PasswordResetController {
//    public PasswordResetController(UtilisateurService utilisateurService) {
//        this.utilisateurService = utilisateurService;
//    }
//
//    @Autowired
//    private final UtilisateurService utilisateurService;
//
//    // Autowiring...
//
//    @PostMapping("/forgot-password")
//    public String processForgotPassword(@RequestParam("email") String email) {
//        utilisateurService.generateResetToken(email);
//        return "redirect:/login?resetLinkSent";
//    }
//
//    @GetMapping("/reset-password")
//    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
//        model.addAttribute("token", token);
//        return "reset-password"; // page pour saisir le nouveau mot de passe
//    }
//
//    @PostMapping("/reset-password")
//    public String processResetPassword(@RequestParam("token") String token,
//                                       @RequestParam("password") String password) {
//        utilisateurService.updatePassword(token, password);
//        return "redirect:/login?passwordResetSuccess";
//    }
//}
