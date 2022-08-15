package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viniciuslj.vote.api.domain.Agenda;
import viniciuslj.vote.api.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
