package upeu.edu.pe.msauth.service.impl;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid;

    @Autowired
    public EmailService(@Value("${sendgrid.api.key}") String sendGridApiKey) {
        this.sendGrid = new SendGrid(sendGridApiKey);
    }

    public void sendEmail(String to, String subject, String body, boolean isHtml) throws IOException {
        Email from = new Email("josue.ochoa@upeu.edu.pe"); // Reemplaza con tu correo electrónico
        Email toEmail = new Email(to);

        // Cambiar el tipo de contenido según si es HTML o texto plano
        Content content;
        if (isHtml) {
            content = new Content("text/html", body); // Contenido HTML
        } else {
            content = new Content("text/plain", body); // Contenido de texto plano
        }

        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        System.out.println("Email sent: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }
}