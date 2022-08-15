package viniciuslj.vote.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viniciuslj.vote.api.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
