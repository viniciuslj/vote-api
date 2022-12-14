package viniciuslj.vote.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import viniciuslj.vote.api.Messages;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Data
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(nullable = false)
    private Long agendaId;

    @Column(name = "member_cpf", length = 11, nullable = false)
    @Pattern(regexp = "^\\d{11}$", message = Messages.Vote.ERROR_INVALID_CPF)
    private String memberCPF;

    @Column(nullable = false)
    private Boolean response;

    @Column(updatable = false)
    @ApiModelProperty(hidden = true)
    private Instant votedAt;
}
