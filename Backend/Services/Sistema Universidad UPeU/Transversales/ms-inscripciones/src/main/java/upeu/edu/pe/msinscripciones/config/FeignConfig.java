package upeu.edu.pe.msinscripciones.config;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.optionals.OptionalEncoder;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FeignConfig {

    @Bean
    public Encoder feignFormEncoder() {
        return new FormEncoder(new OptionalEncoder(new DefaultEncoder()));
    }

    // Encoder por defecto para manejar objetos simples
    private static class DefaultEncoder implements Encoder {
        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) {
            if (object instanceof LocalDate) {
                // Convertir LocalDate a String (por ejemplo, en formato ISO)
                template.body(object.toString());
            } else {
                // Serialización simple por defecto para otros tipos
                Map<String, Object> params = new HashMap<>();
                params.put("data", object);
                template.body(params.toString());
            }
        }
    }
}