package ni.dev.edgeahz.game.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @Column("escape_success")
    private int successfulEscape;

    @Column("escape_failure")
    private int failureEscape;

    @Column
    private int total;
}
