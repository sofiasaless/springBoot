package learningSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication // tem todas as anotações abaixo em uma só

//@EnableAutoConfiguration // erro: 404; fazendo o component scan do pacote ...
//@ComponentScan // erro 500: erro de serelização, necessário get e set nas classes de dominio
//@Configuration
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
