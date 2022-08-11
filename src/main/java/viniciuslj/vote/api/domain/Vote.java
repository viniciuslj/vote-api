package viniciuslj.vote.api.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Vote {
    private Long id;
    private Agenda agenda;
    private Member member;
    private Boolean response;
    private Instant votedAt;
}
