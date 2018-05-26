package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.TeamLeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamManagerDao extends JpaRepository<TeamLeader, Long> {
}
