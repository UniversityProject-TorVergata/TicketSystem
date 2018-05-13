package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.ThirdPartyCustomer;
import isssr.ticketsystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyCustomerDao extends JpaRepository<ThirdPartyCustomer, Long> {
}
