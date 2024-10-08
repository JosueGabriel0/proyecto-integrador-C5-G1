package upeu.edu.pe.msauth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Si estás desarrollando una API REST, puedes permitir todas las solicitudes
        // Sin embargo, asegúrate de aplicar las restricciones necesarias en producción
        http.authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Permitir todas las solicitudes
        );

        // Configuración de CSRF, desactivado para APIs REST
        // Cambiar esta parte según las necesidades de tu aplicación
        http.csrf(csrf -> csrf.disable()); // Desactiva CSRF

        return http.build();
    }
}