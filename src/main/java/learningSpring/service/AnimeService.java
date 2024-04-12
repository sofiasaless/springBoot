package learningSpring.service;

import learningSpring.domain.Anime;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    // salvando animes
    public Anime save(AnimePostRequestBody animePostRequestBody){
        // nao sera mais necessario usar o builder, pois o AnimeMapper vai fazer isso
//        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
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
}
