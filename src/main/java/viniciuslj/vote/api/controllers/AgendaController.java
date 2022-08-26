package viniciuslj.vote.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.services.agenda.CreateAgendaService;
import viniciuslj.vote.api.services.agenda.FindOneAgendaService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/agendas")
@AllArgsConstructor
@Api("Agenda API")
public class AgendaController {

    private final CreateAgendaService createAgendaService;
    private final FindOneAgendaService findOneAgendaService;

    @PostMapping
    @ApiOperation("Create Agenda")
    public ResponseEntity<Agenda> create(@Valid @RequestBody Agenda agenda) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createAgendaService.execute(agenda));
    }

    @GetMapping("{id}")
    @ApiOperation("Find One Agenda by ID")
    public ResponseEntity<Agenda> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(findOneAgendaService.execute(id));
    }
}
