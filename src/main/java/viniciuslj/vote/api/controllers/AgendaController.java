package viniciuslj.vote.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.services.agenda.CreateAgendaService;
import viniciuslj.vote.api.services.agenda.FindOneAgendaService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final CreateAgendaService createAgendaService;
    private final FindOneAgendaService findOneAgendaService;

    public AgendaController(CreateAgendaService createAgendaService, FindOneAgendaService findOneAgendaService) {
        this.createAgendaService = createAgendaService;
        this.findOneAgendaService = findOneAgendaService;
    }

    @PostMapping
    public ResponseEntity<Agenda> create(@Valid @RequestBody Agenda agenda) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createAgendaService.execute(agenda));
    }

    @GetMapping("{id}")
    public ResponseEntity<Agenda> findOne(@PathVariable Long id) {
        return ResponseEntity.of(findOneAgendaService.execute(id));
    }
}
