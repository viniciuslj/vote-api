package viniciuslj.vote.api.services.vote;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Session;
import viniciuslj.vote.api.domain.Vote;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.repository.VoteRepository;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AgendaRepository agendaRepository;


    @InjectMocks
    private CreateVoteService createVoteService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    private Vote makeValidVote(Faker faker, Long agendaId) {
        Vote vote = new Vote();
        vote.setId(faker.number().randomNumber());
        vote.setMemberCPF(faker.number().digits(11));
        vote.setResponse(faker.random().nextBoolean());
        vote.setAgendaId(agendaId);
        return vote;
    }

    private Agenda makeValidAgenda(Faker faker) {
        Agenda agenda = new Agenda();
        agenda.setId(faker.number().randomNumber());
        agenda.setSubject(faker.lorem().sentence());
        return agenda;
    }

    private Session makeValidSession(Faker faker, Agenda agenda) {
        Session session = new Session();
        session.setAgendaId(agenda.getId());
        session.setStartsAt(LocalDateTime.now().minusMinutes(faker.number().randomDigit()));
        session.setEndsAt(LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero()));
        return session;
    }

    @Test
    public void shouldCreateVote() {
        Agenda agenda = makeValidAgenda(faker);
        agenda.setSession(spy(makeValidSession(faker, agenda)));
        Vote vote = makeValidVote(faker, agenda.getId());
        Agenda spyAgenda = spy(agenda);

        given(agendaRepository.findById(agenda.getId())).willReturn(Optional.of(spyAgenda));
        given(voteRepository.save(vote))
                .will(invocationOnMock -> invocationOnMock.getArgument(0));

        Instant now = Instant.now();
        Vote result = createVoteService.execute(vote);

        assertEquals(agenda.getId(), result.getAgendaId());
        assertFalse(result.getVotedAt().isBefore(now));
        then(agendaRepository).should().findById(agenda.getId());
        then(spyAgenda).should().validateSessionExists();
        then(agenda.getSession()).should().validateIsOpen();
        then(voteRepository).should().save(vote);
    }

    @Test
    public void shouldThrowExceptionWhenAgendaNotExist() {
        Agenda agenda = makeValidAgenda(faker);
        agenda.setSession(makeValidSession(faker, agenda));
        Vote vote = makeValidVote(faker, agenda.getId());

        given(agendaRepository.findById(agenda.getId())).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                createVoteService.execute(vote)
        );

        assertEquals(Messages.Agenda.ERROR_NOT_FOUND, exception.getMessage());
        then(agendaRepository).should().findById(agenda.getId());
        then(voteRepository).should(never()).save(any(Vote.class));
    }

    @Test
    public void shouldThrowExceptionWhenSessionNotExist() {
        Agenda agenda = makeValidAgenda(faker);
        Vote vote = makeValidVote(faker, agenda.getId());
        agenda.setSession(null);
        Agenda spyAgenda = spy(agenda);

        given(agendaRepository.findById(agenda.getId())).willReturn(Optional.of(spyAgenda));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                createVoteService.execute(vote)
        );

        assertEquals(Messages.Session.ERROR_NOT_FOUND, exception.getMessage());
        then(agendaRepository).should().findById(agenda.getId());
        then(spyAgenda).should().validateSessionExists();
        then(voteRepository).should(never()).save(any(Vote.class));
    }


    @Test
    public void shouldThrowExceptionWhenSessionNotOpen() {
        List.of(
                List.of(LocalDateTime.now().plusSeconds(faker.number().randomDigitNotZero()), // future start
                        LocalDateTime.now().plusMinutes(faker.number().randomDigitNotZero())  // end
                ),
                List.of(LocalDateTime.now().minusMinutes(faker.number().randomDigitNotZero()), // start
                        LocalDateTime.now().minusSeconds(faker.number().randomDigitNotZero()) // end past
                )
        ).forEach(startEnd -> {
            BDDMockito.reset();
            Agenda agenda = makeValidAgenda(faker);
            agenda.setSession(spy(makeValidSession(faker, agenda)));
            Vote vote = makeValidVote(faker, agenda.getId());
            Agenda spyAgenda = spy(agenda);

            spyAgenda.getSession().setStartsAt(startEnd.get(0));
            spyAgenda.getSession().setEndsAt(startEnd.get(1));

            given(agendaRepository.findById(agenda.getId())).willReturn(Optional.of(spyAgenda));
            BusinessLogicException exception = assertThrows(BusinessLogicException.class, () ->
                    createVoteService.execute(vote)
            );

            assertEquals(Messages.Session.ERROR_NOT_OPEN, exception.getMessage());
            then(agendaRepository).should().findById(agenda.getId());
            then(spyAgenda).should().validateSessionExists();
            then(agenda.getSession()).should().validateIsOpen();
            then(voteRepository).should(never()).save(any(Vote.class));
        });
    }

}
