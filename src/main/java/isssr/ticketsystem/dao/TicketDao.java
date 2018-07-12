package isssr.ticketsystem.dao;


import isssr.ticketsystem.entity.Ticket;
import isssr.ticketsystem.enumeration.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Long> {

     /**
      * To search the ticket opened by a specified user username
      *
      * @param customerID id del customer di cui cercare i ticket
      * @return All tickets opened by a user
      */
     @Query("select t from Ticket t where t.openerUser.id = :customerID")
     List<Ticket> getTicketByOpenerUser(@Param("customerID") Long customerID);

     /**
      * To search the ticket assigned to a specified TeamMember username
      *
      * @param teamLeaderID id del TeamLeader di cui cercare i ticket
      * @return All tickets assigned to a TeamMember
      */
     @Query("select t from Ticket t where t.resolverUser.id = :teamLeaderID")
     List<Ticket> getTicketByResolverUser(@Param("teamLeaderID") Long teamLeaderID);


    /**
     * Per ricercare un ticket in una specifica fase del suo WorkFlow
     *
     * @param ticketState Stato dei Ticket da ricercare nel DB.
     * @return Tutti i ticket nello specifico ticketState
     */
     @Query("select t from Ticket  t where t.currentState = :ticketState")
     List<Ticket> getTicketByState(@Param("ticketState") State ticketState);




}
