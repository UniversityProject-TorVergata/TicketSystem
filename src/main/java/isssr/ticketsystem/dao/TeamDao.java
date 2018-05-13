package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team, Long> {
}
