package isssr.ticketsystem.dao;


import isssr.ticketsystem.entity.Ticket;
import isssr.ticketsystem.entity.TicketState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Long> {

     /**
      * To search the ticket opened by a specified user username
      *
      * @param username
      * @return List<Ticket> : All tickets opened by a user
      */
     @Query("select t from Ticket t where t.openerUser.username = :username")
     List<Ticket> getTicketByOpenerUser(@Param("username") String username);

     /**
      * To search the ticket assigned to a specified TeamMember username
      *
      * @param username
      * @return List<Ticket> : All tickets assigned to a TeamMember
      */
     @Query("select t from Ticket t where t.resolverUser.username = :username")
     List<Ticket> getTicketByResolverUser(@Param("username") String username);


    /**
     * Per ricercare un ticket in una specifica fase del suo WorkFlow
     *
     * @param ticketState Stato dei Ticket da ricercare nel DB.
     * @return List<Ticket> : Tutti i ticket nello specifico ticketState
     */
     @Query("select t from Ticket  t where t.state = :ticketState")
     List<Ticket> getTicketByState(@Param("ticketState") TicketState ticketState);
}
