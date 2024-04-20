package learningSpring.service;

import learningSpring.domain.Anime;
import learningSpring.exception.BadRequestException;
import learningSpring.repository.AnimeRepository;
import learningSpring.util.AnimeCreator;
import learningSpring.util.AnimePostRequestBodyCreator;
import learningSpring.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks // utiliza para a classe que vc quer testar
    private AnimeService animeService;

    @Mock // utiliza para todas as classes que estao no animeService
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
        // vai fazer o setup antes de cada teste
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("List All returns list of animes inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String exceptedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(exceptedName);

    }

    @Test
    @DisplayName("List returns list of animes when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful(){
        String exceptedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("FindById returns anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful(){
        Long exceptedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(exceptedId).isEqualTo(exceptedId);
    }

    @Test
    @DisplayName("FindById throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("FindByName returns anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        String exceptedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("FindByName returns an empty list of anime when is not found")
    void findByName_ReturnsAnEmptyListOfAnime_WhenIsNotFound(){
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        String exceptedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace returns anime when successful")
    void replace_ReturnsAnime_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete returns anime when successful")
    void delete_ReturnsAnime_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }
}