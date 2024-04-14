package learningSpring.repository;

import learningSpring.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// REPOSITORY: interface com querys, representando a conexão com o banco de dados

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    // os metodos abaixo vao criar automaticamente o comando SELECT

    // request params : adicionando parametros na url para a busca de animes por nome, por exemplo
    List<Anime> findByName(String name);

}
