package learningSpring.controller;

import javax.validation.Valid;
import learningSpring.domain.Anime;
import learningSpring.requests.AnimePostRequestBody;
import learningSpring.requests.AnimePutRequestBody;
import learningSpring.service.AnimeService;
import learningSpring.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    // 14/04 - agora retornando um page
    @GetMapping
    public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll(){
        return ResponseEntity.ok(animeService.listAllNonPageable());
    }

    // como o método será acessado pelo getmapping
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/by-id/{id}")
    public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable long id,
                                                                 @AuthenticationPrincipal UserDetails userDetails){
        log.info(userDetails);
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    // publicando "postando" objetos
    // ta esperando um requestBody
    @PostMapping
    // através do valid eu falo ao spring q eu quero a validação automática, dps de implementar lá no animepostrequestbody
//    @PreAuthorize("hasRole('ADMIN')") // colocando segurança em um método ...
    // antes de executar esse método é necessário pre-autorizar com a role admin ou user, no caso aqui será ADMIN, se o usuário for ADMIN poderá executar o método
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
    @DeleteMapping(path = "/admin/{id}")
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
