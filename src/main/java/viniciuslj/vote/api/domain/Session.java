package viniciuslj.vote.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Session {
    @Id
    @JsonIgnore
    private Long agendaId;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "agenda_id")
    @JsonIgnore
    private Agenda agenda;
}
