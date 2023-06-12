package ni.dev.edgeahz.game.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "register")
public class Register {

    @Id
    private Long id;

    private String prison;

    @Column(value = "can_escape")
    private Boolean escape;

    @CreatedDate
    private Timestamp created;

    @LastModifiedDate
    private Timestamp updated;
}
