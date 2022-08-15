package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viniciuslj.vote.api.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
