package learningSpring.repository;

import learningSpring.domain.Anime;

import java.util.List;

// REPOSITORY: interface com querys, representando a conexão com o banco de dados

public interface AnimeRepository {
    List<Anime> listAll();

}
