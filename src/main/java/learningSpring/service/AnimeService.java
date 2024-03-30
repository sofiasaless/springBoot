package learningSpring.service;

import learningSpring.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// SERVICE: é onde fica a lógica de negócio
@Service // para transformar em spring bean
public class AnimeService {
    //private final AnimeRepository animeRepository;
    private static List<Anime> animes;
    static {
        animes = new ArrayList<>(List.of(new Anime(1l,"Boku no Hero"), new Anime(2l,"Berserk")));
    }

    public List<Anime> listAll(){
        return animes;
    }

    // procurando o anime pelo id
    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    // salvando animes
    public Anime save(Anime anime){
        anime.setId(ThreadLocalRandom.current().nextLong(3,100000));
        animes.add(anime);
        return anime;
    }

    public void delete(long id){
        // usando o findById, caso o anime não exista ele lança o bad request de anime not found
        animes.remove(findById(id));
    }
}
