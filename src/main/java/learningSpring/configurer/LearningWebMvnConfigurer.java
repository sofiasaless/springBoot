package learningSpring.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class LearningWebMvnConfigurer implements WebMvcConfigurer {

    // trocando uma configuração global da api
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        // na requisição HTTP vai começar a exibir da pág 0 e exibir 5 animes por vez
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(pageHandler);
    }
}
