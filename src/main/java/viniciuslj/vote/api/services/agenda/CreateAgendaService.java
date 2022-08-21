package viniciuslj.vote.api.services.agenda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.repository.AgendaRepository;

@Service
@Slf4j
public class CreateAgendaService {
    private final AgendaRepository agendaRepository;

    public CreateAgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Agenda execute(Agenda agenda) {
        log.info("Create Agenda ({})", agenda);
        return agendaRepository.save(agenda);
    }
}
