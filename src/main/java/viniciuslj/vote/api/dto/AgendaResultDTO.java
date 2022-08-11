package viniciuslj.vote.api.dto;

import lombok.Data;
import viniciuslj.vote.api.domain.Agenda;

@Data
public class AgendaResultDTO {
    private Agenda agenda;
    private boolean approved;
    private long totalYes;
    private long totalNo;
}
