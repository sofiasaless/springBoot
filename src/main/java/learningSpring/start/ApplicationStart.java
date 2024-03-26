package learningSpring.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
// erro: 404; fazendo o component scan do pacote ...
@ComponentScan(basePackages = "learningSpring")
// erro 500: erro de serelização, necessário get e set nas classes de dominio
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
