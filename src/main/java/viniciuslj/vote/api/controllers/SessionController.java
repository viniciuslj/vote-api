package viniciuslj.vote.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viniciuslj.vote.api.domain.Session;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @PostMapping
    public ResponseEntity<Session> open(@RequestBody Session session) {
        return ResponseEntity.ok(session);
    }
}
