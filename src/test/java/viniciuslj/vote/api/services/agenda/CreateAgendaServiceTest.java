package viniciuslj.vote.api.services.agenda;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.repository.AgendaRepository;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAgendaServiceTest {
    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private CreateAgendaService createAgendaService;

    private Faker faker;

    private Agenda makeValidAgenda(Faker faker, boolean setId) {
        Agenda agenda = new Agenda();
        agenda.setId(setId ? faker.number().randomNumber() : null);
        agenda.setSubject(faker.lorem().sentence());
        agenda.setDescription(faker.lorem().paragraph());
        return agenda;
    }

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    public void shouldCreateAgenda() {
        Agenda agenda = makeValidAgenda(faker, false);
        Long id = faker.number().randomNumber();

        given(agendaRepository.save(agenda)).will(invocationOnMock -> {
            Agenda agendaArg = invocationOnMock.getArgument(0, Agenda.class);
            agendaArg.setId(id);
            return agendaArg;
        });

        Agenda result = createAgendaService.execute(agenda);

        assertEquals(id, result.getId());
        then(agendaRepository).should().save(agenda);
    }

    @Test
    public void shouldThrowExceptionWhenAgendaContainsId() {
        Agenda agenda = makeValidAgenda(faker, true);
        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () ->
                createAgendaService.execute(agenda)
        );

        assertEquals(Messages.Agenda.ERROR_ID_NOT_NULL, exception.getMessage());
        then(agendaRepository).should(never()).save(any(Agenda.class));
    }
}