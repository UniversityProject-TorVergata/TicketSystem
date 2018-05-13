package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDao extends JpaRepository<Ticket, Long> {
}
