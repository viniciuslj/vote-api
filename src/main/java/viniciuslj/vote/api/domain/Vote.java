package viniciuslj.vote.api.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Agenda agenda;

    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false)
    private Boolean response;

    @Column(nullable = false)
    private Instant votedAt;
}
