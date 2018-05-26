package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.TeamCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductManagerDao extends JpaRepository<TeamCoordinator, Long> {
}
