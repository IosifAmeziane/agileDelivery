package sda.projectManagementTool.projectManagement.service.implementation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sda.projectManagementTool.projectManagement.service.EmailService;

@Service
public class EmailServiceImplementation implements EmailService {

    private JavaMailSender javaMailSender;

    public EmailServiceImplementation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String emailAddress, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sda_final_project@gmail.com");
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("Confirmation email");
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
