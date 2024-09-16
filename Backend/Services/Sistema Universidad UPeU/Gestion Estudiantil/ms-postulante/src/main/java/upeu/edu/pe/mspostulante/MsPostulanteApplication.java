package upeu.edu.pe.mspostulante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPostulanteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPostulanteApplication.class, args);
    }

}
