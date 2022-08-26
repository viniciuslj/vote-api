package viniciuslj.vote.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viniciuslj.vote.api.domain.Session;
import viniciuslj.vote.api.services.session.OpenSessionService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Api("Session API")
public class SessionController {
    private final OpenSessionService openSessionService;

    @PostMapping
    @ApiOperation("Open Session")
    public ResponseEntity<Session> open(@Valid @RequestBody Session session) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(openSessionService.execute(session));
    }
}
