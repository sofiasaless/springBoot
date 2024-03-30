package learningSpring.controller;

import learningSpring.domain.Anime;
import learningSpring.service.AnimeService;
import learningSpring.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

// CONTROLLER: representa basicamente onde fica os end points

// anotação que fala que a classe é um controller, o retorno dos métodos dessa classe vão ser apenas strings
// vai retornar arquivos json

@RestController
// o requestmapping vai "nomear" a pagina q vai listar
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor // criando construtor com lombok com argumentos finais
public class AnimeController {
    // injeção de dependências: @Autowired (aqui não precisa)
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    // end point que vai listar
    // localhost:8080/anime
    @GetMapping
    public List<Anime> list(){
        log.info(dateUtil.formatLocalDateTimeToDatebaseStyle(LocalDateTime.now()));
        return animeService.listAll();
    }

}
