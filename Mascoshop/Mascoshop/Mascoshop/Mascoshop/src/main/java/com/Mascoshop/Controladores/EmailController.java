package com.Mascoshop.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mascoshop.Controladores.AuthController.UsuarioRequest;

@Controller
public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/gmail")
    public ResponseEntity<String> sendEmail(@RequestBody UsuarioRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject("Bienvenido MascoLovers");
            message.setText("¡Eres el gran ganador de 50% de descuento en toda nuestra tienda, aprovecha!");

            mailSender.send(message);
            return ResponseEntity.ok("Correo enviado exitosamente");
        } catch (Exception e) {
            e.printStackTrace(); // Esto ayudará a ver el error en los logs
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}