package upeu.edu.pe.mscuentafinancierauniversitaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCuentaFinancieraUniversitariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCuentaFinancieraUniversitariaApplication.class, args);
    }

}
