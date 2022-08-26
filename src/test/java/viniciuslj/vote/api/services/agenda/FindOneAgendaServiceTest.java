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
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FindOneAgendaServiceTest {
    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private FindOneAgendaService findOneAgendaService;

    private Agenda agenda;

    @BeforeEach
    void setUp() {
        Faker faker = new Faker();
        agenda = new Agenda();
        agenda.setSubject(faker.lorem().sentence());
        agenda.setDescription(faker.lorem().paragraph());
        agenda.setId(faker.number().randomNumber());
    }

    @Test
    public void shouldFindOneAgendaById() {
        given(agendaRepository.findById(agenda.getId())).willReturn(Optional.of(agenda));

        Agenda result = findOneAgendaService.execute(agenda.getId());

        assertEquals(agenda, result);
        then(agendaRepository).should().findById(agenda.getId());
    }

    @Test
    public void ShouldThrowExceptionWhenAgendaNotExist() {
        given(agendaRepository.findById(anyLong())).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                findOneAgendaService.execute(anyLong())
        );

        assertEquals(Messages.Agenda.ERROR_NOT_FOUND, exception.getMessage());
        then(agendaRepository).should().findById(anyLong());
    }
}
