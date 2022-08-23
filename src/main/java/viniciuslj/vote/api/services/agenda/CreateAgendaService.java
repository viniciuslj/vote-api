package viniciuslj.vote.api.services.agenda;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.repository.AgendaRepository;

@Service
@Slf4j
@AllArgsConstructor
public class CreateAgendaService {
    private final AgendaRepository agendaRepository;

    public Agenda execute(Agenda agenda) {
        log.info("Create Agenda ({})", agenda);
        return agendaRepository.save(agenda);
    }
}
