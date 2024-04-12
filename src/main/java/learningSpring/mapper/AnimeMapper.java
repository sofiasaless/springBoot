package learningSpring.mapper;

import learningSpring.domain.Anime;
import learningSpring.requests.AnimePostRequestBody;
import learningSpring.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    // metodo para chamar os metodos
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    // os metodos abaixo fazem a conversão automatica para objetos anime
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);


}
