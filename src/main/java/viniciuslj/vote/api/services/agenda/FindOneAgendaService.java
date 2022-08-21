package viniciuslj.vote.api.services.agenda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.repository.AgendaRepository;
import java.util.Optional;

@Service
@Slf4j
public class FindOneAgendaService {
    private final AgendaRepository agendaRepository;

    public FindOneAgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Optional<Agenda> execute(Long id) {
        log.debug("Find One Agenda by id = {}", id);
        return agendaRepository.findById(id);
    }
}
