package com.example.engelvinmaroc.services;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ByteArrayResource;

@Service
public class EmailService {

    @Autowired
     JavaMailSender javaMailSender;

    public void sendEmailWithAttachment(String to, String subject, String text, String s, byte[] pdfContent) throws MessagingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("elmehdi.aderab@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("Commande Engelvin Maroc.pdf", new ByteArrayResource(pdfContent));

        javaMailSender.send(message);
    }


//    public void sendResetPasswordEmail(String toEmail, String token) {
//        String resetPasswordLink = "http://localhost:8080/reset-password?token=" + token;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Réinitialisation de mot de passe");
//        message.setText("Pour réinitialiser votre mot de passe, cliquez sur le lien suivant : " + resetPasswordLink);
//
//        javaMailSender.send(message);
//    }
}
