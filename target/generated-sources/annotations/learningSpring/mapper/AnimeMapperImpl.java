package learningSpring.mapper;

import javax.annotation.processing.Generated;
import learningSpring.domain.Anime;
import learningSpring.requests.AnimePostRequestBody;
import learningSpring.requests.AnimePutRequestBody;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-03T16:40:59-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class AnimeMapperImpl extends AnimeMapper {

    @Override
    public Anime toAnime(AnimePostRequestBody animePostRequestBody) {
        if ( animePostRequestBody == null ) {
            return null;
        }

        Anime anime = new Anime();

        anime.setName( animePostRequestBody.getName() );

        return anime;
    }

    @Override
    public Anime toAnime(AnimePutRequestBody animePutRequestBody) {
        if ( animePutRequestBody == null ) {
            return null;
        }

        Anime anime = new Anime();

        anime.setId( animePutRequestBody.getId() );
        anime.setName( animePutRequestBody.getName() );

        return anime;
    }
}
