package learningSpring.service;

import jakarta.transaction.Transactional;
import learningSpring.domain.Anime;
import learningSpring.exception.BadRequestException;
import learningSpring.mapper.AnimeMapper;
import learningSpring.repository.AnimeRepository;
import learningSpring.requests.AnimePostRequestBody;
import learningSpring.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// SERVICE: é onde fica a lógica de negócio
@Service // para transformar em spring bean
// para o spring fazer a injeção de dependências do AnimeRepository
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    // procurando o anime pelo id
    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    // salvando animes
    // - para caso seu método precise de um row back, é necessario a anotação de transactional
    // o transactional não funciona corretamente para exceptions do tipo check, é preciso adicionar (rollbackFor = Exception.class)
    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody){
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));


        // TESTANDO TRANSAÇÕES

//        Anime anime = animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
//
//        //testando transações
//        if(true)
//            throw new RuntimeException("bad code");
//        return anime;

    }

    public void delete(long id){
        // usando o findById, caso o anime não exista ele lança o bad request de anime not found
//        animes.remove(findById(id));
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        // o AnimeMapper tambem vai ser utilizado no replace
        Anime animeSaved = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(animeSaved.getId());
        animeRepository.save(anime);

//        delete(anime.getId());
//        animes.add(anime);
    }

    // o metodo do repository vai duplicar aqui para funcionar
    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

}
