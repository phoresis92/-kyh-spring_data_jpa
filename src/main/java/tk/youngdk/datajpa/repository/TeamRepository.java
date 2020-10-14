package tk.youngdk.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.youngdk.datajpa.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
