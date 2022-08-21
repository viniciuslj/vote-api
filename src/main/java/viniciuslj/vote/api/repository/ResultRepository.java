package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viniciuslj.vote.api.domain.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
