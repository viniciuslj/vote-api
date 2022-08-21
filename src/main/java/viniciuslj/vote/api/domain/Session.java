package viniciuslj.vote.api.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Session {
    @Id
    @PrimaryKeyJoinColumn
    private Long agendaId;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    private LocalDateTime endsAt;
}
