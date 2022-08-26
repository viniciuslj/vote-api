package viniciuslj.vote.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viniciuslj.vote.api.domain.Vote;
import viniciuslj.vote.api.services.vote.CreateVoteService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/votes")
@AllArgsConstructor
@Api("Vote API")
public class VoteController {
    private final CreateVoteService createVoteService;

    @PostMapping
    @ApiOperation("To Vote")
    public ResponseEntity<Vote> toVote(@Valid @RequestBody Vote vote) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createVoteService.execute(vote));
    }
}
