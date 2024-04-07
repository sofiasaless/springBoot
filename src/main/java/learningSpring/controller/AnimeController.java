package learningSpring.controller;

import learningSpring.domain.Anime;
import learningSpring.service.AnimeService;
import learningSpring.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatebaseStyle(LocalDateTime.now()));
        // retornando o status da requisição
//        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
        return ResponseEntity.ok(animeService.listAll());
    }

    // como o método será acessado pelo getmapping
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return ResponseEntity.ok(animeService.findById(id));
    }

    // publicando "postando" objetos
    // ta esperando um requestBody
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody Anime anime){
        animeService.save(anime);
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    // deletando objetos
    // é necessário colocar o (path = "/{id}") para identificar que há uma entrada ... estava dando esse erro

//    {
//        "timestamp": "2024-03-30T21:37:02.645+00:00",
//            "status": 405,
//            "error": "Method Not Allowed",
//            "message": "Method 'DELETE' is not supported.",
//            "path": "/animes/39255"
//    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // o RequestBody significa a necessidade de escrever o objeto em json para determinada ação
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Anime anime){
        animeService.replace(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
