package learningSpring.controller;

import learningSpring.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// anotação que fala que a classe é um controller, o retorno dos métodos dessa classe vão ser apenas strings
// vai retornar arquivos json

@RestController
@RequestMapping("anime")
public class AnimeController {

    // end point que vai listar
    // localhost:8080/anime/list
    // o requestmapping vai "nomear" a pagina q vai listar
    @GetMapping(path = "list")
    public List<Anime> list(){
        return List.of(new Anime("DBZ"), new Anime("Berserk"));
    }

}
