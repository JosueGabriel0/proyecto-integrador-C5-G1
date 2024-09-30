package upeu.edu.pe.msgatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");  // Permitir tu frontend
        config.addAllowedHeader("*");  // Permitir todos los encabezados, incluyendo Authorization
        config.addAllowedMethod("*");  // Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Aplicar configuración a todas las rutas

        return new CorsWebFilter(source);
    }
}