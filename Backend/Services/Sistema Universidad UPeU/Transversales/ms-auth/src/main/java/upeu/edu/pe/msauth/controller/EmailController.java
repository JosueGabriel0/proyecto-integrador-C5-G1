package upeu.edu.pe.msauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msauth.service.impl.EmailService;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(defaultValue = "false") boolean isHtml) {
        try {
            emailService.sendEmail(to, subject, body, isHtml);
            return ResponseEntity.ok("Email sent successfully");
        } catch (IOException e) {
            e.printStackTrace(); // Puedes manejar la excepción de manera más adecuada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}