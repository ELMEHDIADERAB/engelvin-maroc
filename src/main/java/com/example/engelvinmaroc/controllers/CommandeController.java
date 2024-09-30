package com.example.engelvinmaroc.controllers;


import com.example.engelvinmaroc.entities.*;
import com.example.engelvinmaroc.repositories.ArticleRepository;
import com.example.engelvinmaroc.repositories.CommandeRepository;
import com.example.engelvinmaroc.repositories.FournisseurRepository;
import com.example.engelvinmaroc.services.CommandeService;
import com.example.engelvinmaroc.services.EmailService;

import com.example.engelvinmaroc.services.PDFService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
public class CommandeController {


    @Autowired
    private CommandeService commandeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PDFService pdfService;

//    @PostMapping("/commande/valider/{id}")
//    public String validerCommande(@PathVariable Long id) throws IOException {
//        List<Object[]> commandeDetails = commandeService.getCommandeByID(id);
//        commandeService.ValiderCommande(id);
//        if (!commandeDetails.isEmpty()) {
//            Object[] commande = commandeDetails.get(0);
//            String emailFournisseur = (String) commande[11]; // Assuming the 12th element is the email of the fournisseur
//
//            String article = (String) commande[6];
//            Long idCommande = (Long) commande[3];
//            String date =  commande[5].toString();
//            byte[] pdfBytes = pdfService.generateCommandePDF(commandeDetails);
//
//            try {
//                emailService.sendEmailWithAttachment(
//                        emailFournisseur,
//                        "Validation de Commande",
//                        "Nous avons le plaisir de vous informer que la commande suivante a été validée :\n" +
//                                "\n" +
//                                "    Bon de commande :"+idCommande+" \n" +
//                                "    Date de commande :"+date + "\n" +
//                                "    Articles commandés : "+article+"\n" +
//                                "\n" +
//                                "Veuillez trouver les détails complets de la commande en pièce jointe.\n" +
//                                "\n" +
//                                "Nous vous remercions pour votre collaboration et restons à votre disposition pour toute information supplémentaire.\n" +
//                                "\n" +
//                                "Cordialement,.",
//                        //     "Votre commande a été validée. Veuillez trouver les détails en pièce jointe.",
//                        "commande.pdf",
//                        pdfBytes
//                );
//                //return "commandesList";
//                return "redirect:/commandesList";
//                //return "/commandesList";
//            } catch (MessagingException e) {
//                e.printStackTrace();
//                return "Erreur lors de l'envoi de l'email.";
//            }
//        } else {
//            return "Commande non trouvée.";
//        }
//    }

    @GetMapping("/api/commandes/statistiques")
    public Map<Integer, Map<Integer, Long>> getCommandesParMois() {
        return commandeService.getCommandesParMois();
    }


//    @GetMapping("/api/commandes/statistiques")
//    public Map<Integer, Long> getCommandesParMois() {
//        return commandeService.getCommandesParMois();
//    }


//    @Autowired
//    private CommandeRepository commandeRepository;
//    @Autowired
//    private CommandeService commandeService;
//
//    @Autowired
//    private FournisseurRepository fournisseurRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private EmailService emailService;
//    @Autowired
//    private PDFService pdfService;
//
//    @PostMapping("/commande/valider/{id}")
//    public String validerCommande(@PathVariable Long id) throws IOException {
//       // Commande commande = commandeRepository.findById(id).orElse(null);
//
//        List<Object[]> commande = commandeService.getCommandeByID(id);
//        if (commande != null) {
//            byte[] baos = pdfService.generateCommandePDF(commande);
//            try {
//                emailService.sendEmailWithAttachment(
//                        "aderab.elmehdi@gmail.com",
//                        "Validation de Commande",
//                        "Votre commande a été validée. Veuillez trouver les détails en pièce jointe.",
//                     baos
//                     //   String.valueOf(baos)
//                );
//                return "redirect:/commandes";
//            } catch (MessagingException e) {
//                e.printStackTrace();
//                return "Erreur lors de l'envoi de l'email.";
//            }
//        } else {
//            return "Commande non trouvée.";
//        }
//    }

    ///////////////////////

//    @PostMapping("/commande/valider/{id}")
//    public String validerCommande(@PathVariable Long id) throws MessagingException {
//        Commande commande = commandeRepository.findById(id).orElse(null);
//        Fournisseur fournisseur = fournisseurRepository.findById(id).orElse(null);
//        Article article = articleRepository.findById(id).orElse(null);
//        if (commande != null) {
//          //  Fournisseur fournisseur = fournisseurRepository.findById(commande.getFournisseurId()).orElse(null);
//            //Article article = articleRepository.findById(commande.getArticleId()).orElse(null);
//
//
//                // Générer le PDF
//                ByteArrayOutputStream baos = PdfGenerator.generatePdf(commande, fournisseur, article);
//
//                // Envoyer l'email avec le PDF en pièce jointe
//
//                    emailService.sendEmailWithAttachment(fournisseur.getEmail(), "Validation de Commande", "Veuillez trouver ci-joint le bon de commande validé.", baos);
//
//
//        }
//        return "redirect:/commande/bon/" + id;
//    }
}




//
//import com.example.engelvinmaroc.entities.Commande;
//import com.example.engelvinmaroc.services.CommandeService;
//import com.example.engelvinmaroc.services.EmailService;
//import com.example.engelvinmaroc.services.PDFService;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.io.ByteArrayOutputStream;
//
//@RestController
//@RequestMapping("/commandes")
//public class CommandeController {
//
//    @Autowired
//    private CommandeService commandeService;
//
//    @Autowired
//    private PDFService pdfService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/valider/{id}")
//    public String validerCommande(@PathVariable Long id) {
//        Commande commande = commandeService.getCommandeById(id);
//
//        if (commande != null) {
//            ByteArrayOutputStream baos = pdfService.generateCommandePDF(commande);
//            try {
//                emailService.sendEmailWithAttachment(
//                        commande.getUtilisateur().getEmail(),
//                        "Validation de Commande",
//                        "Votre commande a été validée. Veuillez trouver les détails en pièce jointe.",
//                        baos
//                );
//                return "Commande validée et email envoyé.";
//            } catch (MessagingException e) {
//                e.printStackTrace();
//                return "Erreur lors de l'envoi de l'email.";
//            }
//        } else {
//            return "Commande non trouvée.";
//        }
//    }
//}




//import com.example.engelvinmaroc.entities.Article;
//import com.example.engelvinmaroc.entities.Commande;
//import com.example.engelvinmaroc.entities.Fournisseur;
//import com.example.engelvinmaroc.repositories.ArticleRepository;
//import com.example.engelvinmaroc.repositories.CommandeRepository;
//import com.example.engelvinmaroc.repositories.FournisseurRepository;
//import com.example.engelvinmaroc.services.EmailService;
//import com.example.engelvinmaroc.utils.PdfGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.mail.MessagingException;
//import java.io.ByteArrayOutputStream;
//import java.util.Date;
//
//@Controller
//public class CommandeController {
//
//    @Autowired
//    private CommandeRepository commandeRepository;
//
//    @Autowired
//    private FournisseurRepository fournisseurRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @GetMapping("/commande/bon/{id}")
//    public String afficherBonCommande(@PathVariable Long id, Model model) {
//        Commande commande = commandeRepository.findById(id).orElse(null);
//        if (commande != null) {
//            model.addAttribute("idCommande", commande.getId());
//            model.addAttribute("date", new Date());
//            Fournisseur fournisseur = fournisseurRepository.findById(commande.getFournisseurId()).orElse(null);
//            if (fournisseur != null) {
//                model.addAttribute("nomFournisseur", fournisseur.getNom());
//                model.addAttribute("adresseFournisseur", fournisseur.getAdresse());
//                model.addAttribute("telephoneFournisseur", fournisseur.getTelephone());
//            }
//            Article article = articleRepository.findById(commande.getArticleId()).orElse(null);
//            if (article != null) {
//                model.addAttribute("articleTitle", article.getTitle());
//                model.addAttribute("unite", article.getUnite());
//                model.addAttribute("quantite", commande.getQuantite());
//                model.addAttribute("prixUHT", article.getPrixUHT());
//            }
//        }
//        return "bonCommande";
//    }
//
//    @PostMapping("/commande/valider/{id}")
//    public String validerCommande(@PathVariable Long id) {
//        Commande commande = commandeRepository.findById(id).orElse(null);
//        if (commande != null) {
//            Fournisseur fournisseur = fournisseurRepository.findById(commande.getFournisseurId()).orElse(null);
//            Article article = articleRepository.findById(commande.getArticleId()).orElse(null);
//
//            if (fournisseur != null && article != null) {
//                // Générer le PDF
//                ByteArrayOutputStream baos = PdfGenerator.generatePdf(commande, fournisseur, article);
//
//                // Envoyer l'email avec le PDF en pièce jointe
//                try {
//                    emailService.sendEmailWithAttachment(fournisseur.getEmail(), "Validation de Commande", "Veuillez trouver ci-joint le bon de commande validé.", baos);
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "redirect:/commande/bon/" + id;
//    }
//}
