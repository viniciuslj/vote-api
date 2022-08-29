package viniciuslj.vote.api.services.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Result;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.repository.ResultRepository;
import viniciuslj.vote.api.repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AgendaClosingMonitor {
    private final AgendaRepository agendaRepository;
    private final VoteRepository voteRepository;
    private final ResultRepository resultRepository;

    private final List<AgendaClosingListener> listeners;

    public AgendaClosingMonitor(
            AgendaRepository agendaRepository,
            VoteRepository voteRepository,
            ResultRepository resultRepository) {
        this.agendaRepository = agendaRepository;
        this.voteRepository = voteRepository;
        this.resultRepository = resultRepository;

        listeners = new ArrayList<>();
    }

    @Scheduled(fixedRate = 1000, initialDelay = 0)
    public void scan() {
        try {
            agendaRepository
                    .findAllClosedAndNoResults()
                    .forEach(this::processResults);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void processResults(Agenda agenda) {
        log.debug("Process results of Agenda id: {}", agenda.getSubject());
        Result result = new Result();
        try {
            result.setTotals(voteRepository.countResultYesAndNo(agenda.getId()));
            result.setAgendaId(agenda.getId());
            result.setApproved(result.getTotalYes() > result.getTotalNo());

            resultRepository.save(result);
            updateAllListeners(result);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void addListener(AgendaClosingListener listener) {
        listeners.add(listener);
    }

    private void updateAllListeners(Result result) {
        listeners.forEach(listener -> listener.update(result));
    }
}
