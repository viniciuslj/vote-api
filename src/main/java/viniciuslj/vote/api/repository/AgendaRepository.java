package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viniciuslj.vote.api.domain.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
