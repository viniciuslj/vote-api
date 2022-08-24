package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import viniciuslj.vote.api.domain.Agenda;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("""
                select a from Agenda a
                  left join fetch a.session s
                  left join fetch a.result r
                where s is not null
                  and r is null
                  and s.endsAt < current_timestamp
                    order by s.endsAt asc
            """)
    List<Agenda> findAllClosedAndNoResults();
}
