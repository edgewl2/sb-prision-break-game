package ni.dev.edgeahz.game.domain.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrisonBreakReportResponse {

    @JsonProperty("count_successful_escape")
    private int countSuccessfulEscape;

    @JsonProperty("count_unsuccessful_escape")
    private int countUnsuccessfulEscape;

    private float ratio;
}
