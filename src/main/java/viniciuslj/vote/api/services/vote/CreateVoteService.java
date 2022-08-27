package viniciuslj.vote.api.services.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Vote;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.repository.VoteRepository;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import viniciuslj.vote.api.services.exceptions.UnauthorizedException;
import viniciuslj.vote.api.vendor.services.user.UserInformation;
import viniciuslj.vote.api.vendor.services.user.UserInformationService;

import java.time.Instant;

@Service
@Slf4j
@AllArgsConstructor
public class CreateVoteService {
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final UserInformationService userInformationService;

    public Vote execute(Vote vote) {
        log.debug("Create Vote ({})", vote);

        try {
            Agenda agenda = agendaRepository
                    .findById(vote.getAgendaId())
                    .orElseThrow(() -> new EntityNotFoundException(Messages.Agenda.ERROR_NOT_FOUND));

            agenda.validateSessionExists();
            agenda.getSession().validateIsOpen();

            if (!memberIsAuthorized(vote.getMemberCPF())) {
                throw new UnauthorizedException(Messages.Vote.ERROR_UNAUTHORIZED_MEMBER);
            }

            vote.setVotedAt(Instant.now());
            return voteRepository.save(vote);

        } catch (DataIntegrityViolationException e) {
            throw new BusinessLogicException(Messages.Vote.ERROR_MEMBER_VOTE_EXISTS);
        }
    }

    private boolean memberIsAuthorized(String memberCPF) {
        try {
            return userInformationService.get(memberCPF).getStatus()
                    .equals(UserInformation.STATUS.ABLE);
        } catch (UnauthorizedException exception) {
            return false;
        }
    }
}
