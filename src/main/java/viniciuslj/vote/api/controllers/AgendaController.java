package viniciuslj.vote.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.dto.AgendaResultDTO;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    @PostMapping
    public ResponseEntity<Agenda> create(@RequestBody Agenda agenda) {
        return ResponseEntity.ok(agenda);
    }

    @GetMapping("{id}/result")
    public ResponseEntity<AgendaResultDTO> getResult(@PathVariable Long id) {
        return ResponseEntity.ok(new AgendaResultDTO());
    }
}
