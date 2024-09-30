package com.example.engelvinmaroc.controllers;


import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.engelvinmaroc.entities.*;
import com.example.engelvinmaroc.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Controller
public class ArticleController {
    private  RoleUtilisateurService roleUtilisateurService;
    private ArticleService articleService;
    private FournisseurService fournisseurService;
    private UtilisateurService utilisateurService;

    private Article_CommandeService article_commandeService;
    private CommandeService commandeService;
//    @Autowired
//    private PdfService pdfService;
//
//    @Autowired
//    private EmailService emailService;
@Autowired
private EmailService emailService;

    @Autowired
    private PDFService pdfService;

    public ArticleController(RoleUtilisateurService roleUtilisateurService, ArticleService articleService,
                             FournisseurService fournisseurService, UtilisateurService utilisateurService,
                             @Qualifier("article_CommandeServiceImp")  Article_CommandeService articleCommandeService, CommandeService commandeService) {
        this.roleUtilisateurService = roleUtilisateurService;
        this.articleService = articleService;
        this.fournisseurService = fournisseurService;
        this.utilisateurService = utilisateurService;
        this.article_commandeService = articleCommandeService;
        this.commandeService = commandeService;
    }
    @ModelAttribute
    public void addCommonUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            Utilisateur utilisateur = utilisateurService.loadUserByUsername(username);

            if (utilisateur != null) {
                model.addAttribute("nom", utilisateur.getNom());
                model.addAttribute("prenom", utilisateur.getPrenom());
                model.addAttribute("email", utilisateur.getEmail());
              //  model.addAttribute("role", utilisateur.getRoles());

            }
        }
    }

    @RequestMapping("/denied")
    public String Denied(){
        return "denied";
    }

    @RequestMapping("/login")
    public  String login(){
        return "SeConnecter";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest)throws ServletException {
        httpServletRequest.logout();
        return "redirect:/login";
    }
    @RequestMapping("/dashboard")
    public String index(ModelMap modelMap,@RequestParam(required = false)Fournisseur fournisseur) {
        List<Commande> commandesV=commandeService.getCommandeValide();
        modelMap.addAttribute("commandesValide", commandesV.size());

        List<Commande> commandesNonV=commandeService.getCommandeNonValide();
        modelMap.addAttribute("commandesNonValide", commandesNonV.size());

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//        return "redirect:/login";}
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        modelMap.addAttribute("fournisseursVue",fournisseurs.size());
        List<Article> articles = articleService.getAllArticles(fournisseur);
        modelMap.addAttribute("articlesVueSize",articles.size());
        return "dashboard";
    }
    @RequestMapping("ajouterArticle")
    public String ajouterArticle(ModelMap modelMap) {

        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        modelMap.addAttribute("fournisseurs", fournisseurs);
        return "AjouterArticle";
    }

    @RequestMapping("/saveArticle")
    private String saveArticle(@Valid Article article,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "AjouterArticle";
        boolean articleExiste = articleService.existbyNomAndFournisseur(article.getTitle(),article.getFournisseur());

        if (articleExiste) {
          //  redirectAttributes.addFlashAttribute("msgArticleE", "Article"+article.getTitle() + article.getFournisseur() +" existe déjà.");
            redirectAttributes.addFlashAttribute("msgArticleE", "L'article " + article.getTitle() + " avec le fournisseur " + article.getFournisseur().getNom() + " existe déjà.");
            return "redirect:/ajouterArticle";
        }
        articleService.save(article);
        redirectAttributes.addFlashAttribute("messageArticleA", "L'article a été ajouté avec succès.");
        return "redirect:articlesList";
       // return "articlesList" ;
    }

@RequestMapping("/articlesList")
private String ArticleList(ModelMap modelMap, @RequestParam(required = false) Fournisseur fournisseur) {
    List<Article> articles = articleService.getAllArticles(fournisseur);
    modelMap.addAttribute("articlesVue", articles);
    if (articles.isEmpty()) {
        modelMap.addAttribute("noArticlesMessage", "Ce fournisseur n'a pas d'articles.");
    }

    List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
    modelMap.addAttribute("fournisseurs", fournisseurs);
    modelMap.addAttribute("selectedFournisseur", fournisseur); // Ajouter l'attribut selectedFournisseur
    return "articlesList";
}


    @RequestMapping("/deleteArticle")
    private String deleteArticle(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        if (articleService.isArticleInArticleCommande(id)) {
            redirectAttributes.addFlashAttribute("messageArticleE", "Impossible de supprimer l'article car il est associé à une ou plusieurs commandes.");
            return "redirect:/articlesList";
        }

        articleService.deleteArticleById(id);
        redirectAttributes.addFlashAttribute("messageArticleS", "L'article a été supprimé avec succès.");
        return "redirect:/articlesList";
    }


    @RequestMapping("/editArticle")
    private String editArticle(@RequestParam("id")Long id, ModelMap modelMap) {
        Article article = articleService.getArticleById(id);
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        modelMap.addAttribute("fournisseurs", fournisseurs);
        modelMap.addAttribute("articleVue",article);
        return "editArticle";
}


@RequestMapping("updateArticle")
public String updateArticle(@ModelAttribute("articleVue") Article article, ModelMap modelMap, @RequestParam(required = false) Fournisseur fournisseur, RedirectAttributes redirectAttributes) {
    articleService.Update(article);
    redirectAttributes.addFlashAttribute("messageArticleE", "L'article a été modifié avec succès.");
    // Redirection vers la liste des articles
    return "redirect:articlesList";
}


    //partie fournisser
    @RequestMapping("ajouterFournisseur")
    public String ajouterFournisseur() {
        return "AjouterFournisseur";
    }



    @PostMapping("/saveFournisseur")
    public String saveFournisseur(@Valid Fournisseur fournisseur, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/ajouterFournisseur";
        }

        boolean fournisseurExiste = fournisseurService.existsByNomOrEmailOrTelephone(fournisseur.getNom(),fournisseur.getEmail(), fournisseur.getTelephone());

        if (fournisseurExiste) {
            redirectAttributes.addFlashAttribute("msgFournisseurE", "Fournisseur "+fournisseur.getNom()+" existe déjà.");
            return "redirect:/ajouterFournisseur";
        }

        try {
            fournisseurService.save(fournisseur);
            redirectAttributes.addFlashAttribute("messageFournisseurA", "Fournisseur a été ajouté avec succès.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("msgFournisseurE", "Fournisseur existe déjà.");
            return "redirect:/ajouterFournisseur";
        }


        return "redirect:/fournisseursList";
    }



    @RequestMapping("/fournisseursList")
    private String FournisseurList(ModelMap modelMap) {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        modelMap.addAttribute("fournisseursVue",fournisseurs);
        return "fournisseursList";
    }
    @RequestMapping("/deleteFournisseur")
    private String deleteFournisseur(@RequestParam("id")Long id, ModelMap modelMap,RedirectAttributes redirectAttributes) {
        fournisseurService.deleteFournisseurById(id);
        redirectAttributes.addFlashAttribute("messageFournisseurS", "Le fournisseur a été supprimé avec succès.");
        return "redirect:/fournisseursList";

        //return FournisseurList(modelMap) ;
    }

    @RequestMapping("/editFournisseur")
    private String editFournisseur(@RequestParam("id")Long id, ModelMap modelMap) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        modelMap.addAttribute("fournisseurVue",fournisseur);
        return "editFournisseur";
    }


    @RequestMapping("updateFournisseur")
    private String updateFournisseur(@ModelAttribute("fournisseurVue") Fournisseur fournisseur, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        boolean emailExistePourAutreFournisseur = fournisseurService.existsByEmailAndNotId(fournisseur.getEmail(), fournisseur.getId());

        if (emailExistePourAutreFournisseur) {
            redirectAttributes.addFlashAttribute("msgFournisseurE", "Un autre fournisseur avec cet email existe déjà.");
            return "redirect:/editFournisseur?id=" + fournisseur.getId();
        }

        // Mettre à jour le fournisseur
        fournisseurService.update(fournisseur);
        redirectAttributes.addFlashAttribute("messageFournisseurE", "Le fournisseur a été modifié avec succès.");
        return "redirect:/fournisseursList";
    }


                                                        //controller utilisateur
    //---------------------------------------------------------------------------------------------------------
    @RequestMapping("/utilisateurs")
    public String utilisateurs(ModelMap modelMap) {
        List<Utilisateur> utilisateurs=utilisateurService.findAll();
        modelMap.addAttribute("utilisateursVue",utilisateurs);
        return "utilisateursList";
    }
    @RequestMapping("/ajouterUtilisateur")
    public String ajouterUser(ModelMap modelMap){
        List<RoleUtilisateur> rolesutilisateurs = roleUtilisateurService.getAllRoleUtilisateur();
        modelMap.addAttribute("rolesutilisateursVue",rolesutilisateurs);
        return "AjouterUtilisateur";
    }


@RequestMapping("/saveUtilisateur")
private String saveUtilisateur(@Valid Utilisateur utilisateur, ModelMap modelMap, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
//        return "AjouterUtilisateur";
        return "redirect:/ajouterUtilisateur";
    }

    boolean utilisateurExiste = utilisateurService.existUtilisateur(utilisateur.getEmail(), utilisateur.getNom(), utilisateur.getPrenom());

    if (!isPasswordStrong(utilisateur.getPassword())) {
        redirectAttributes.addFlashAttribute("msgUtilisateurE", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");
        return "redirect:/ajouterUtilisateur";
    }
    if (utilisateurExiste) {
        redirectAttributes.addFlashAttribute("msgUtilisateurE", "L'utilisateur existe déjà.");
        return "redirect:/ajouterUtilisateur";
    }

    try {
        utilisateurService.saveUtilisateur(utilisateur);
        redirectAttributes.addFlashAttribute("msgUtilisateurA", "L'utilisateur a été ajouté avec succès.");
    } catch (DataIntegrityViolationException e) {
        redirectAttributes.addFlashAttribute("msgUtilisateurE", "L'utilisateur existe déjà.");
        return "redirect:/ajouterUtilisateur";
    }

    return "redirect:/utilisateurs";
}

    @RequestMapping("/deleteUtilisateur")
    private String deleteUtilisateur(@RequestParam("id")Long id, ModelMap modelMap,RedirectAttributes redirectAttributes) {
        utilisateurService.deleteUtilisateurById(id);
        redirectAttributes.addFlashAttribute("msgUtilisateurS", "L'utilisateur a été supprimé avec succès.");
return "redirect:/utilisateurs";

    }

    @RequestMapping("/editUtilisateur")
    public String editUtilisateur(@RequestParam("id") Long id, ModelMap modelMap) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        modelMap.addAttribute("utilisateurVue", utilisateur);
        return "editUtilisateur";
    }

    @PostMapping("/updateUtilisateur")
    public String updateUtilisateur(@ModelAttribute("utilisateurVue") Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        if (utilisateurService.existUtilisateur(utilisateur.getEmail(), utilisateur.getNom(), utilisateur.getPrenom())) {
            redirectAttributes.addFlashAttribute("errorMsg", "L'utilisateur avec ces informations existe déjà.");
            return "redirect:/editUtilisateur?id=" + utilisateur.getId();
        }
        utilisateurService.updateUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getId());
        redirectAttributes.addFlashAttribute("msgUtilisateurE", "L'utilisateur a été modifié avec succès.");
        return "redirect:/utilisateurs";
    }
    //commande controller
    @RequestMapping("commander")
    public String commander(ModelMap modelMap,@RequestParam(required = false)Fournisseur fournisseur) {
        List<Article> articles = articleService.getAllArticles(fournisseur);
        modelMap.addAttribute("articlesVue",articles);
        return "commander";
    }

    @RequestMapping("BonCommande")
    public String BonCommande(ModelMap modelMap, @RequestParam("id") Long id) {
        List<Object[]> commandeById = commandeService.getCommandeByID(id);
        if (!commandeById.isEmpty()) {
            Object[] commande = commandeById.get(0);

            // Vérifiez que les valeurs sont bien des doubles
            double prixUHT = Double.parseDouble(commande[9].toString());
            double quantite = Double.parseDouble(commande[4].toString());

            // Calculer le montant total
            double montantTotal = quantite * prixUHT * 1.2;
            double tva=(quantite*prixUHT)*20/100;
            double montantHT = quantite * prixUHT;
            // Créer un DecimalFormat avec le format français
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
            symbols.setGroupingSeparator(' '); // Séparateur pour les milliers
            symbols.setDecimalSeparator(',');  // Séparateur décimal

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
            String formattedMontant = decimalFormat.format(montantTotal);
            String formatTva = decimalFormat.format(tva);
            String formatHT = decimalFormat.format(montantHT);
            String formatPrixHt =decimalFormat.format(prixUHT);
            // Ajouter les attributs au modèle
            modelMap.addAttribute("adresseFournisseur", commande[0]);
            modelMap.addAttribute("nomFournisseur", commande[1]);
            modelMap.addAttribute("telephoneFournisseur", commande[2]);
            modelMap.addAttribute("idCommande", commande[3]);
            modelMap.addAttribute("quantite", commande[4]);
            modelMap.addAttribute("date", commande[5]);
            modelMap.addAttribute("articleTitle", commande[6]);
            modelMap.addAttribute("idArticle", commande[7]);
            modelMap.addAttribute("idArticleCommande", commande[8]);
            modelMap.addAttribute("prixUHT", commande[9]);
            modelMap.addAttribute("unite", commande[10]);
            modelMap.addAttribute("commentaire", commande[12]);
            modelMap.addAttribute("validation", commande[13]);
            modelMap.addAttribute("montantTotal", formattedMontant);
            modelMap.addAttribute("tva", formatTva);
            modelMap.addAttribute("montantHT", formatHT);
            modelMap.addAttribute("formatPrixHt", formatPrixHt);


            return "detailsCommande";
        } else {
            return "redirect:commande";
        }
    }


            @RequestMapping(value = "/ajouterCommande", method = RequestMethod.POST)
            public String ajouterCommande(@Valid @ModelAttribute("article_commande") Article_Commande article_commande,
                                          BindingResult bindingResult,
                                          ModelMap modelMap,
                                          @RequestParam(required = false) Fournisseur fournisseur,
                                          RedirectAttributes redirectAttributes) {
                // Vérifier les erreurs de validation
                if (bindingResult.hasErrors()) {
                    List<Article> articles = articleService.getAllArticles(fournisseur);
                    modelMap.addAttribute("articlesVue", articles);
                    modelMap.addAttribute("msgQuantite", "La quantité doit être supérieure ou égale à 1");
                    return "commander"; // Renvoie directement à la page de commande
                }

                // Ajouter la commande si pas d'erreurs
                article_commandeService.ajouterCommande(article_commande);
                redirectAttributes.addFlashAttribute("messageCommande", "Commande effectuée avec succès");
               // return "redirect:/commander";
                return "redirect:/commande";
            }






    @RequestMapping("/commande")
    public String Commandes(ModelMap modelMap){
        List<Object[]> commandes = commandeService.getAllCommandesT();
        if (!commandes.isEmpty()) {


        commandes.forEach(row -> System.out.println(row[2]));
        modelMap.addAttribute("commandesVue", commandes);
        return "commandesList";}
        else {
            modelMap.addAttribute("msgcommandevide","Aucun commande pour l'instant");
            return "commandesList";
        }
    }
    @RequestMapping("/commandes")
    public String allCommandes(ModelMap modelMap){
        List<Object[]> commandes = commandeService.getAllCommandesT();
        modelMap.addAttribute("commandesVue", commandes);
        return "commandes";
    }
    @RequestMapping("/deleteCommande")
    public String deleteCommande(@RequestParam("id")Long id, ModelMap modelMap) {
        commandeService.deleteCommandeById(id);
        return allCommandes(modelMap);
    }
    @RequestMapping("modifierProfile")
    public String modifierProfile(){
        return "editProfile";
    }
    @PostMapping("updateProfile")
    public String updateProfile(@RequestParam("nom")String nom,
                                @RequestParam("prenom") String prenom,
                                @RequestParam("email")String email,
                                RedirectAttributes redirectAttributes) {
      utilisateurService.updateProfile(nom,prenom,email);
        redirectAttributes.addFlashAttribute("message", "Profil mis à jour avec succès");
      return "redirect:/modifierProfile";
    }






    @PostMapping("/updatepassword")
    public String changePassword(@RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("messagePE", "Les mots de passe sont différents.");
                return "redirect:/modifierProfile";
            }

            if (!isPasswordStrong(newPassword)) {
                redirectAttributes.addFlashAttribute("messagePE", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");
                return "redirect:/modifierProfile";
            }

            utilisateurService.changePassword(newPassword);
            redirectAttributes.addFlashAttribute("messageP", "Mot de passe mis à jour avec succès.");
            return "redirect:/modifierProfile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messagePE", "Une erreur est survenue lors de la mise à jour du mot de passe.");
            return "redirect:/modifierProfile";
        }
    }

    private boolean isPasswordStrong(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        return pattern.matcher(password).matches();
    }


    //validation de commande
    @PostMapping("/commande/valider/{id}")
    public String validerCommande(@PathVariable Long id,RedirectAttributes redirectAttributes) throws IOException {
        List<Object[]> commandeDetails = commandeService.getCommandeByID(id);
        commandeService.ValiderCommande(id);
        if (!commandeDetails.isEmpty()) {
            Object[] commande = commandeDetails.get(0);
            String emailFournisseur = (String) commande[11]; // Assuming the 12th element is the email of the fournisseur

            String article = (String) commande[6];
            Long idCommande = (Long) commande[3];
            //String date =  commande[5].toString();
            Timestamp timestamp = (Timestamp) commande[5]; // Assuming the 6th element is a Timestamp object
            LocalDate date = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = date.format(formatter);
            byte[] pdfBytes = pdfService.generateCommandePDF(commandeDetails);

            try {
                emailService.sendEmailWithAttachment(
                        emailFournisseur,
                        "Nouvelle commande de Engelvin Maroc",
                        "Bonjour,\n" +
                                "\n" +
                                "    Bon de commande : "+idCommande+" \n" +
                                "    Date de commande : "+formattedDate + "\n" +
                                "    Articles commandés : "+article+"\n" +
                                "\n" +
                                "Nous vous remercions par avance de traiter cette commande dans les meilleurs délais. Pour toute question ou information complémentaire, n'hésitez pas à nous contacter.\n" +
                                "\n" +
                                "Cordialement,",
                        //     "Votre commande a été validée. Veuillez trouver les détails en pièce jointe.",
                        "commande.pdf",
                        pdfBytes
                );
                //return "commandesList";
                redirectAttributes.addFlashAttribute("messageCommandeValide","La commande a été validée avec succès et envoyée au fournisseur !");
                return "redirect:/commande";
                //return "/commandesList";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Erreur lors de l'envoi de l'email.";
            }
        } else {
            return "Commande non trouvée.";
        }
    }

    @GetMapping("/articlePlusCommande")
    public String getMostOrderedArticles(Model model) {
        List<Object[]> mostOrderedProducts = article_commandeService.findMostOrderedProducts();
        model.addAttribute("products", mostOrderedProducts);
        return "dashboard";
    }
}
