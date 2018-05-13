package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserDao extends JpaRepository<RegisteredUser, Long> {
}
