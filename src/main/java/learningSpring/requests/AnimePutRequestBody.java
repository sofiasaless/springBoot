// para que não seja permitido enviar o id do anime, já que é feito automaticamente
package learningSpring.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
