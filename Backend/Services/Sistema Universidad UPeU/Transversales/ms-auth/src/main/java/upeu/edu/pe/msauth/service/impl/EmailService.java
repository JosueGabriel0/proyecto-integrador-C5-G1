package upeu.edu.pe.msauth.service.impl;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid;

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public EmailService() {
        this.sendGrid = new SendGrid(sendGridApiKey);
    }

    public void sendEmail(String to, String subject, String body) throws IOException {
        Email from = new Email("josueochoa20203@gmail.com"); // Reemplaza con tu correo electrónico
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        System.out.println("Email sent: " + response.getStatusCode());
    }
}