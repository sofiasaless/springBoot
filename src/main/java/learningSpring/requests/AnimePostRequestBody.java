// para que não seja permitido enviar o id do anime, já que é feito automaticamente
package learningSpring.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostRequestBody {
    // com as duas anotações abaixo o atributo name não poderá ser vazio nem nulo
    @NotEmpty(message = "The anime name cannot be empty")
    @NotNull(message = "The anime name cannot be null")
    private String name;
}
