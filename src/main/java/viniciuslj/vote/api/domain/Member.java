package viniciuslj.vote.api.domain;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String name;
    private String cpf;
}
