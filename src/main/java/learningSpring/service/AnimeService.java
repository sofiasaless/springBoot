package learningSpring.service;

import learningSpring.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// SERVICE: é onde fica a lógica de negócio
@Service // para transformar em spring bean
public class AnimeService {
    //private final AnimeRepository animeRepository;

    private List<Anime> animes = List.of(new Anime(1l,"Boku no Hero"), new Anime(2l,"Berserk"));

    public List<Anime> listAll(){
        return animes;
    }

    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }
}
