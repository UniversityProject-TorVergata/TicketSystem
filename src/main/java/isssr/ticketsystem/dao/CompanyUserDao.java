package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Company;
import isssr.ticketsystem.entity.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserDao extends JpaRepository<CompanyUser, Long> {
}
