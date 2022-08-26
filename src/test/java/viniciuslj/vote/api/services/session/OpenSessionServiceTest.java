package viniciuslj.vote.api.services.session;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.domain.Session;
import viniciuslj.vote.api.repository.SessionRepository;
import viniciuslj.vote.api.repository.constraints.RelationshipViolation;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OpenSessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private RelationshipViolation relationshipViolation;

    @InjectMocks
    private OpenSessionService openSessionService;

    private Session session;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        session = new Session();
        session.setAgendaId(faker.number().randomNumber());
    }

    @Test
    public void shouldOpenSession() {
        session.setStartsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));
        session.setEndsAt(session.getStartsAt().plusMinutes(faker.number().randomDigitNotZero()));

        given(sessionRepository.existsById(session.getAgendaId())).willReturn(false);
        given(sessionRepository.save(session)).willReturn(session);

        Session result = openSessionService.execute(session);

        assertEquals(session, result);
        then(sessionRepository).should().existsById(session.getAgendaId());
        then(sessionRepository).should().save(session);
    }

    @Test
    public void shouldOpenSessionWithEmptyEndsAt() {
        session.setStartsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));

        given(sessionRepository.existsById(session.getAgendaId())).willReturn(false);
        given(sessionRepository.save(any(Session.class)))
                .will(invocationOnMock -> invocationOnMock.getArgument(0));

        Session result = openSessionService.execute(session);

        assertEquals(session.getAgendaId(), result.getAgendaId());
        assertEquals(session.getStartsAt().plusMinutes(1), result.getEndsAt());
        then(sessionRepository).should().existsById(session.getAgendaId());
        then(sessionRepository).should().save(session);
    }

    @Test
    public void shouldThrowExceptionWhenSessionExists() {
        session.setStartsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));
        session.setEndsAt(session.getStartsAt().plusMinutes(faker.number().randomDigitNotZero()));

        given(sessionRepository.existsById(session.getAgendaId())).willReturn(true);

        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () ->
                openSessionService.execute(session)
        );

        assertEquals(Messages.Session.ERROR_EXISTING, exception.getMessage());
        then(sessionRepository).should().existsById(session.getAgendaId());
        then(sessionRepository).should(never()).save(any(Session.class));
    }

    @Test
    public void shouldThrowExceptionWhenStartsAtNotFuture() {
        List.of(
                LocalDateTime.now().minusSeconds(faker.number().randomDigitNotZero()), // start < now()
                LocalDateTime.now() // start == now()
        ).forEach(startsAt -> {
            session.setStartsAt(startsAt);

            BusinessLogicException exception = assertThrows(BusinessLogicException.class, () ->
                    openSessionService.execute(session)
            );

            assertEquals(Messages.Session.ERROR_START_NOT_FUTURE, exception.getMessage());
            then(sessionRepository).should(never()).save(any(Session.class));
        });
    }

    @Test
    public void shouldThrowExceptionWhenEndsAtNotAfterStartsAt() {
        session.setStartsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));

        List.of(
                session.getStartsAt().minusSeconds(faker.number().randomDigitNotZero()), // end < start
                session.getStartsAt() // end == start
        ).forEach(endsAt -> {
            session.setEndsAt(endsAt);

            BusinessLogicException exception = assertThrows(BusinessLogicException.class, () ->
                    openSessionService.execute(session)
            );

            assertEquals(Messages.Session.ERROR_END_NOT_AFTER_START, exception.getMessage());
            then(sessionRepository).should(never()).save(any(Session.class));
        });
    }

    @Test
    public void shouldThrowExceptionWhenAgendaNotExist() {
        session.setStartsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));
        session.setEndsAt(session.getStartsAt().plusMinutes(faker.number().randomDigitNotZero()));

        given(sessionRepository.existsById(session.getAgendaId())).willReturn(false);
        given(sessionRepository.save(session)).willThrow(DataIntegrityViolationException.class);
        given(relationshipViolation.check(
                any(DataIntegrityViolationException.class), any(), any())
        ).willReturn(true);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                openSessionService.execute(session)
        );

        assertEquals(Messages.Agenda.ERROR_NOT_FOUND, exception.getMessage());
        then(sessionRepository).should().existsById(session.getAgendaId());
        then(sessionRepository).should().save(session);
    }
}
