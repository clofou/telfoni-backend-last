package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Code de vérification de votre compte");
        message.setText("Votre code de vérification est : " + verificationCode);

        mailSender.send(message);
    }
}
