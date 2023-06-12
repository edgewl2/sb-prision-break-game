package ni.dev.edgeahz.game.domain.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrisonRequest {

    @NotEmpty(message = "{request.prison.not.empty}")
    @NotNull(message = "{request.prison.not.null}")
    private List<String> prison;
}
