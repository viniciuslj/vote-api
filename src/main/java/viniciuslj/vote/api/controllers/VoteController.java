package viniciuslj.vote.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viniciuslj.vote.api.domain.Vote;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @PostMapping
    public ResponseEntity<Vote> toVote(@RequestBody Vote vote) {
        return ResponseEntity.ok(vote);
    }
}
