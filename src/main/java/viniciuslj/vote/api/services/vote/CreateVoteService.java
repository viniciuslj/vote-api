package viniciuslj.vote.api.services.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Vote;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.repository.VoteRepository;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import java.time.Instant;

@Service
@Slf4j
@AllArgsConstructor
public class CreateVoteService {
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;

    public Vote execute(Vote vote) {
        log.debug("Create Vote ({})", vote);

        try {
            Agenda agenda = agendaRepository.findById(vote.getAgendaId())
                    .orElseThrow(() -> new EntityNotFoundException("Agenda not found"));

            agenda.validateSessionExists();
            agenda.getSession().validateIsOpen();

            vote.setVotedAt(Instant.now());
            return voteRepository.save(vote);

        } catch (BusinessLogicException e) {
            throw e;

        } catch (DataIntegrityViolationException e) {
            throw new BusinessLogicException("The member has already voted on this agenda");
        }
    }
}
