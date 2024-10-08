package upeu.edu.pe.msauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upeu.edu.pe.msauth.service.impl.EmailService;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        try {
            emailService.sendEmail(to, subject, body);
        } catch (IOException e) {
            e.printStackTrace(); // Aquí puedes manejar la excepción de manera más adecuada
            // Podrías lanzar una respuesta adecuada al cliente, por ejemplo:
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}