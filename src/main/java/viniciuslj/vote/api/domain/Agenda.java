package viniciuslj.vote.api.domain;

import lombok.Data;

@Data
public class Agenda {
    private Long id;
    private String subject;
    private String description;
}
