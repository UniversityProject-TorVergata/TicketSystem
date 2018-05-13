package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserDao extends JpaRepository<AdminUser, Long> {
}
