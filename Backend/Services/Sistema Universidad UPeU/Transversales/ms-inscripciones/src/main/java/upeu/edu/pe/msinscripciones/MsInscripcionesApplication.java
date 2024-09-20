package upeu.edu.pe.msinscripciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsInscripcionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsInscripcionesApplication.class, args);
    }

}
