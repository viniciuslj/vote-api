package viniciuslj.vote.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    private String description;

    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Session session;
}
