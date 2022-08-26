package viniciuslj.vote.api.services.session;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Session;
import viniciuslj.vote.api.repository.SessionRepository;
import viniciuslj.vote.api.repository.constraints.RelationshipViolation;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import viniciuslj.vote.api.services.exceptions.UnknownServiceException;

@Service
@Slf4j
@AllArgsConstructor
public class OpenSessionService {
    private final SessionRepository sessionRepository;
    private final RelationshipViolation relationshipViolation;

    public Session execute(Session session) {
        log.info("Open Session ({})", session);

        try {
            session.setDefaultEndsAtIfNull();
            session.validatePeriod();

            if (sessionRepository.existsById(session.getAgendaId())) {
                throw new BusinessLogicException(Messages.Session.ERROR_EXISTING);
            }

            return sessionRepository.save(session);

        } catch (BusinessLogicException e) {
            throw e;

        } catch (DataIntegrityViolationException e) {
            if (relationshipViolation.check(e, Session.class, Agenda.class)){
                throw new EntityNotFoundException(Messages.Agenda.ERROR_NOT_FOUND);
            }

            throw new UnknownServiceException(e.getMessage());
        }
    }
}

