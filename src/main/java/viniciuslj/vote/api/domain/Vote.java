package viniciuslj.vote.api.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Data
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long agendaId;

    @Column(name = "member_cpf", length = 11, nullable = false)
    @Pattern(regexp = "^\\d{11}$", message = "Invalid member CPF")
    private String memberCPF;

    @Column(nullable = false)
    private Boolean response;

    @Column(updatable = false)
    private Instant votedAt;
}
