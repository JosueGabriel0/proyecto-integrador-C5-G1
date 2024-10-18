package upeu.edu.pe.msinscripciones.configuration;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Configurar timeouts, interceptores, etc. aquí si es necesario.
        restTemplate.setInterceptors(Collections.singletonList(myInterceptor()));
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    private ClientHttpRequestInterceptor myInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(
                    HttpRequest request,
                    byte[] body,
                    ClientHttpRequestExecution execution) throws IOException {
                // Lógica de interceptor aquí (ej. agregar headers, log etc.)
                return execution.execute(request, body);
            }
        };
    }

    private static class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
            return statusCode != null && (statusCode.is4xxClientError() || statusCode.is5xxServerError());
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // Manejo de errores aquí
            // Puedes lanzar una excepción o registrar el error
        }
    }
}
