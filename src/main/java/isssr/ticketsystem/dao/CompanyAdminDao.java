package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdminDao extends JpaRepository<Admin, Long> {
}
