package viniciuslj.vote.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.services.agenda.CreateAgendaService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final CreateAgendaService createAgendaService;

    public AgendaController(CreateAgendaService createAgendaService) {
        this.createAgendaService = createAgendaService;
    }

    @PostMapping
    public ResponseEntity<Agenda> create(@Valid @RequestBody Agenda agenda) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createAgendaService.execute(agenda));
    }

    @GetMapping("{id}/result")
    public ResponseEntity<AgendaResultDTO> getResult(@PathVariable Long id) {
        return ResponseEntity.ok(new AgendaResultDTO());
    }
}
