// ao executar os testes, o seguinte warning aparece:
// OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
// nao interfere no desempenho dos testes, mas é bom tentar entender o que é isso e resolver o warning

package learningSpring.repository;

import javax.validation.ConstraintViolationException;
import learningSpring.domain.Anime;
import learningSpring.util.AnimeCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists Anime when Successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // testando se o anime não é nulo
        Assertions.assertThat(animeSaved).isNotNull();

        // testando se o id no anime não é nulo
        Assertions.assertThat(animeSaved.getId()).isNotNull();

        // testando se o nome do anime que tem que ser salvo é igual ao anime salvo
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates Anime when Successful")
    void save_UpdateAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // alterando o nome do anime para fazer update
        animeSaved.setName("Overlord");

        // criando anime do update
        Anime animeUpdated = this.animeRepository.save(animeSaved);

//        log.info(animeUpdated.getName());
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes Anime when Successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        // validando se o animeOptional ta vazio, se tiver é pq foi deletado
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of Anime when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        // testando se a lista ta vazia e se contem o animeSaved
        Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);

        // outra forma de fazer
//        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
//                .isThrownBy(() -> this.animeRepository.save(anime))
//                .withMessageContaining("The anime name cannot be empty");
    }

    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("cloroquina");

        // testando se a lista ta vazia, deve retornar que esta vazio, pois cloroquina nao é nome de anime
        Assertions.assertThat(animes).isEmpty();
    }

    // passando para o pacote util
//    private Anime AnimeCreator.createAnimeToBeSaved(){
//        return Anime.builder()
//                .name("Hajime no Ippo")
//        .build();
//    }

}