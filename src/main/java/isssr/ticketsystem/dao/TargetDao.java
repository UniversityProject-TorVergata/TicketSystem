package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetDao extends JpaRepository<Target, Long> {
}
