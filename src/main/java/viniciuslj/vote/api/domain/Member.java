package viniciuslj.vote.api.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
}
