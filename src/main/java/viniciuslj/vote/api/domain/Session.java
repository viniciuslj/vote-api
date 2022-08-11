package viniciuslj.vote.api.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Session {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Agenda agenda;
}
