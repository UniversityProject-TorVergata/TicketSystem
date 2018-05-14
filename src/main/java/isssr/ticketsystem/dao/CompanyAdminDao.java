package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdminDao extends JpaRepository<CompanyAdmin, Long> {
}
