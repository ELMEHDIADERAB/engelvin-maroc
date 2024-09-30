package com.example.engelvinmaroc.services;


import com.example.engelvinmaroc.entities.Article_Commande;
import com.example.engelvinmaroc.entities.Commande;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Service
public class PDFService {

    public byte[] generateCommandePDF(List<Object[]> commandeDetails) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Adding the logo
        try (InputStream logoStream = getClass().getResourceAsStream("/static/assets/images/unnamed.png")) {
            if (logoStream != null) {
                Image logo = new Image(ImageDataFactory.create(logoStream.readAllBytes()));
                logo.setFixedPosition(40, 760);
                logo.scaleToFit(150, 100);
                document.add(logo);
            } else {
                System.err.println("Unable to load image: Logo file not found in classpath");
            }
        } catch (IOException e) {
            System.err.println("Unable to load image: " + e.getMessage());
        }

        // Header section
        document.add(new Paragraph("BON DE COMMANDE : "+ commandeDetails.get(0)[8])
                .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT));

//        document.add(new Paragraph("N° " + commandeDetails.get(0)[8])
//                .setFont(PdfFontFactory.createFont("Helvetica"))
//                .setFontSize(12)
//                .setTextAlignment(TextAlignment.RIGHT));

        // Affaire section
        document.add(new Paragraph("AFFAIRE : ADM BACKBONE" )
                .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));



        // Drawing a line
        PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
      //  canvas.moveTo(30, 720).lineTo(570, 720).stroke();

        // Information Table
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
        infoTable.setWidth(UnitValue.createPercentValue(100));
        infoTable.setMarginTop(40);
        // Company Information
        Cell companyCell = new Cell();
        companyCell.add(new Paragraph("ENGELVIN MAROC").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(12));
        companyCell.add(new Paragraph("ICE: 003390959000079").setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        companyCell.add(new Paragraph("SIEGE SOCIAL: 109 Route Bouskoura Drabna 2ème étage N°13").setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        companyCell.add(new Paragraph("SIDI MAAROUF").setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        companyCell.add(new Paragraph("CASABLANCA").setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        companyCell.add(new Paragraph("Tel: 05 20 63 01 27").setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        infoTable.addCell(companyCell);

        // Supplier Information
        Cell supplierCell = new Cell();
        supplierCell.add(new Paragraph("Fournisseur: " + commandeDetails.get(0)[1]).setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(12));
        supplierCell.add(new Paragraph("Adresse: " + commandeDetails.get(0)[0]).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        supplierCell.add(new Paragraph("Tel: " + commandeDetails.get(0)[2]).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
        infoTable.addCell(supplierCell);

        document.add(infoTable);

        // Date section
        Date date = (Date) commandeDetails.get(0)[5];
        String formattedDate = dateFormat.format(date);
        document.add(new Paragraph("Date: " + formattedDate)
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(15));

        // Drawing a line
        //canvas.moveTo(30, 640).lineTo(570, 640).stroke();

        // Table Header
        float[] columnWidths = {1, 5, 2, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setMarginTop(30);
        //
        table.addHeaderCell(new Cell().add(new Paragraph("Poste").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
        table.addHeaderCell(new Cell().add(new Paragraph("Désignation").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
        table.addHeaderCell(new Cell().add(new Paragraph("Unité").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantité").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
        table.addHeaderCell(new Cell().add(new Paragraph("PU HT").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));
        table.addHeaderCell(new Cell().add(new Paragraph("Montant HT").setFont(PdfFontFactory.createFont("Helvetica-Bold"))));

        // Table Content
        double totalHT = 0.0;
        int position = 1;
        for (Object[] detail : commandeDetails) {
            table.addCell(String.valueOf(position++));
            table.addCell(detail[6].toString());
            table.addCell(detail[10].toString());
            table.addCell(detail[4].toString());
            table.addCell(detail[9].toString());
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
            symbols.setGroupingSeparator(' '); // Séparateur pour les milliers
            symbols.setDecimalSeparator(',');  // Séparateur décimal

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
            double montantHT = Double.parseDouble(detail[4].toString()) * Double.parseDouble(detail[9].toString());
            totalHT += montantHT;
            table.addCell(decimalFormat.format(montantHT));
//            double montantHT = Double.parseDouble(detail[4].toString()) * Double.parseDouble(detail[9].toString());
//            totalHT += montantHT;
//            table.addCell(String.format("%.2f", montantHT));
        }
        document.add(table);

        // Totals Table
        Table totalsTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
        totalsTable.setWidth(UnitValue.createPercentValue(60));
        totalsTable.setMarginTop(20);
        //totalsTable.setMarginLeft(10);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
        symbols.setGroupingSeparator(' '); // Séparateur pour les milliers
        symbols.setDecimalSeparator(',');  // Séparateur décimal

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        double tva = totalHT * 0.20;
        double totalTTC = totalHT + tva;

        totalsTable.addCell(new Cell().add(new Paragraph("TOTAL MONTANT HT:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
        totalsTable.addCell(new Cell().add(new Paragraph(decimalFormat.format(totalHT)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));

        totalsTable.addCell(new Cell().add(new Paragraph("TVA 20%:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
        totalsTable.addCell(new Cell().add(new Paragraph(decimalFormat.format(tva)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));

        totalsTable.addCell(new Cell().add(new Paragraph("TOTAL MONTANT TTC:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
        totalsTable.addCell(new Cell().add(new Paragraph(decimalFormat.format(totalTTC)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));

//        double tva = totalHT * 0.20;
//        double totalTTC = totalHT + tva;
//
//        totalsTable.addCell(new Cell().add(new Paragraph("TOTAL MONTANT HT:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
//        totalsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", totalHT)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));
//
//        totalsTable.addCell(new Cell().add(new Paragraph("TVA 20%:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
//        totalsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", tva)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));
//
//        totalsTable.addCell(new Cell().add(new Paragraph("TOTAL MONTANT TTC:").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(10)));
//        totalsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", totalTTC)).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10)));

        document.add(totalsTable);



        // commentaire Table
//        if(commandeDetails.get(0)[12] != null) {
//            Table commentaire = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
//            commentaire.setWidth(UnitValue.createPercentValue(100));
//            commentaire.setMarginTop(20);
//            Cell commentaireCell = new Cell();
//            commentaireCell.add(new Paragraph("Commentaires").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(12));
//            commentaireCell.add(new Paragraph(" " + commandeDetails.get(0)[12]).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
//            commentaire.addCell(commentaireCell);
//            document.add(commentaire);
//        }
        String commentaire = (String) commandeDetails.get(0)[12];
        if (commentaire != null && !commentaire.trim().isEmpty()) {
            Table commentaireTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
            commentaireTable.setWidth(UnitValue.createPercentValue(100));
            commentaireTable.setMarginTop(20);
            Cell commentaireCell = new Cell();
            commentaireCell.add(new Paragraph("Commentaires").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(12));
            commentaireCell.add(new Paragraph(" " + commentaire).setFont(PdfFontFactory.createFont("Helvetica")).setFontSize(10));
            commentaireTable.addCell(commentaireCell);
            document.add(commentaireTable);
        }





        // Signature
        Paragraph signatureParagraph = new Paragraph("Signature (Nom, Qualité et Cachet): A.OURHOU (chargé d'affaires)")
                .setFont(PdfFontFactory.createFont("Helvetica"))
                .setFontSize(10)
                .setMarginTop(30);

        document.add(signatureParagraph);

        // Adding the signature image
        try (InputStream signatureStream = getClass().getResourceAsStream("/static/assets/images/signature.png")) {
            if (signatureStream != null) {
                Image signatureImage = new Image(ImageDataFactory.create(signatureStream.readAllBytes()));
                signatureImage.scaleToFit(150, 100);
                document.add(signatureImage.setFixedPosition(350, 100));
            } else {
                System.err.println("Unable to load image: Signature file not found in classpath");
            }
        } catch (IOException e) {
            System.err.println("Unable to load image: " + e.getMessage());
        }

        // Footer
        canvas.setFontAndSize(PdfFontFactory.createFont("Helvetica"), 10);
        canvas.beginText();
        canvas.setTextMatrix(30, 30);
        canvas.showText("SIEGE SOCIAL: 109 ROUTE BOUSKOURA DRABNA SIDI MAAROUF, CASABLANCA");
        canvas.setTextMatrix(30, 20);
        canvas.showText("CAPITAL SOCIAL: 1 000 000.00 DH | IF: 54033660 | RC: 601597 | ICE: 003390959000079 | TP: 32905329");
        canvas.endText();

        // Closing the document
        document.close();

        return baos.toByteArray();
    }


}
