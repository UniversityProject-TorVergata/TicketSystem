package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.TeamManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamManagerDao extends JpaRepository<TeamManager, Long> {
}
