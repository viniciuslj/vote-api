package viniciuslj.vote.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Result {
    @Id
    @PrimaryKeyJoinColumn
    private Long agendaId;

    @Column(nullable = false)
    private boolean approved;

    @Column(nullable = false)
    private long totalYes;

    @Column(nullable = false)
    private long totalNo;
}
