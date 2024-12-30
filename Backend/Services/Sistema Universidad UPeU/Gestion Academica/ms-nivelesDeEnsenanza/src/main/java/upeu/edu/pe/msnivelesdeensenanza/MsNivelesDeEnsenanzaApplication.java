package upeu.edu.pe.msnivelesdeensenanza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsNivelesDeEnsenanzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNivelesDeEnsenanzaApplication.class, args);
    }

}
