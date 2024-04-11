// para que não seja permitido enviar o id do anime, já que é feito automaticamente
package learningSpring.requests;

import lombok.Data;

@Data
public class AnimePostRequestBody {
    private String name;
}
