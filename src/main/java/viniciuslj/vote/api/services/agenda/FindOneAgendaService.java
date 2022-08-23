package viniciuslj.vote.api.services.agenda;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class FindOneAgendaService {
    private final AgendaRepository agendaRepository;

    public Agenda execute(Long id) {
        log.debug("Find One Agenda by id = {}", id);

        return agendaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Agenda not found")
        );
    }
}
