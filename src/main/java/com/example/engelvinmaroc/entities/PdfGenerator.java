//package com.example.engelvinmaroc.entities;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//
//import java.io.ByteArrayOutputStream;
//
//public class PdfGenerator {
//    public static ByteArrayOutputStream generatePdf(Commande commande, Fournisseur fournisseur, Article article, Article_Commande articleCommande) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        PdfWriter writer = new PdfWriter(baos);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        document.add(new Paragraph("Bon de Commande"));
//        document.add(new Paragraph("Commande ID: " + commande.getId()));
//        document.add(new Paragraph("Date: " + commande.getDate()));
//        document.add(new Paragraph("Téléphone: " + fournisseur.getTelephone()));
//        document.add(new Paragraph("Article: " + article.getTitle()));
//        document.add(new Paragraph("Prix Unitaire HT: " + article.getPrix_Unitaire_HT()));
//        document.add(new Paragraph("Quantité: " + articleCommande.getQuantite()));
//        document.add(new Paragraph("Montant HT: " + articleCommande.getQuantite() * article.getPrix_Unitaire_HT()));
//        document.add(new Paragraph("TVA 20%: " + (articleCommande.getQuantite() * article.getPrix_Unitaire_HT()) * 0.2));
//        document.add(new Paragraph("Total TTC: " + (articleCommande.getQuantite() * article.getPrix_Unitaire_HT()) * 1.2));
//
//        document.close();
//        return baos;
//    }
//}
