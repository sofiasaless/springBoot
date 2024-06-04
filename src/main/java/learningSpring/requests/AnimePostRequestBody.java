// para que não seja permitido enviar o id do anime, já que é feito automaticamente
package learningSpring.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
    // com as duas anotações abaixo o atributo name não poderá ser vazio nem nulo
    @NotEmpty(message = "The anime name cannot be empty")
    @NotNull(message = "The anime name cannot be null")
    private String name;
//    @URL(message = "The URL is not valid")
//    private String url;

}
