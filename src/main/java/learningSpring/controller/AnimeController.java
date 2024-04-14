package learningSpring.controller;

import jakarta.validation.Valid;
import learningSpring.domain.Anime;
import learningSpring.requests.AnimePostRequestBody;
import learningSpring.requests.AnimePutRequestBody;
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
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    // publicando "postando" objetos
    // ta esperando um requestBody
    @PostMapping
    // através do valid eu falo ao spring q eu quero a validação automática, dps de implementar lá no animepostrequestbody
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
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
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // necessario o /list para nao gerar ambiguidade com o findById
    // com o request param não é necessario o /{name}, na url de requisição vai ficar :
    // /animes/find?anime=steins gate
    @GetMapping(path = "/find")
    public ResponseEntity<List <Anime>> findByName(@RequestParam String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }

}
