package learningSpring.service;

import learningSpring.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

// SERVICE: é onde fica a lógica de negócio
@Service // para transformar em spring bean
public class AnimeService {
    //private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return List.of(new Anime(1l,"Boku no Hero"), new Anime(2l,"Berserk"));
    }
}
