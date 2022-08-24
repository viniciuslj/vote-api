package viniciuslj.vote.api.services.result;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Result;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.repository.ResultRepository;
import viniciuslj.vote.api.repository.VoteRepository;

@Slf4j
@Component
@AllArgsConstructor
public class AgendaClosingMonitor {
    private final AgendaRepository agendaRepository;
    private final VoteRepository voteRepository;
    private final ResultRepository resultRepository;

    @Scheduled(fixedRate = 1000, initialDelay = 0)
    public void scan() {
        try {
            agendaRepository.findAllClosedAndNoResults()
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

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
