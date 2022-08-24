package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import viniciuslj.vote.api.domain.Vote;
import java.util.Map;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("""
                select
                    sum(case when v.response is true then 1 else 0 end) as totalYes,
                    sum(case when v.response is false then 1 else 0 end) as totalNo
                from Vote v
                where v.agendaId = ?1
            """)
    Map<String, Long> countResultYesAndNo(Long agendaId);
}
