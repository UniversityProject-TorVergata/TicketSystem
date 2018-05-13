package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyDao extends JpaRepository<Company, Long> {
}
