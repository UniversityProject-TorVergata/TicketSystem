package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyCustomerDao extends JpaRepository<Customer, Long> {
}
