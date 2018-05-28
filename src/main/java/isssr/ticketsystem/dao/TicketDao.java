package isssr.ticketsystem.dao;


import isssr.ticketsystem.entity.TAG;
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
      * @param customerID
      * @return List<Ticket> : All tickets opened by a user
      */
     @Query("select t from Ticket t where t.openerUser.id = :customerID")
     List<Ticket> getTicketByOpenerUser(@Param("customerID") Long customerID);

     /**
      * To search the ticket assigned to a specified TeamMember username
      *
      * @param teamLeaderID
      * @return List<Ticket> : All tickets assigned to a TeamMember
      */
     @Query("select t from Ticket t where t.resolverUser.id = :teamLeaderID")
     List<Ticket> getTicketByResolverUser(@Param("teamLeaderID") Long teamLeaderID);


    /**
     * Per ricercare un ticket in una specifica fase del suo WorkFlow
     *
     * @param ticketState Stato dei Ticket da ricercare nel DB.
     * @return List<Ticket> : Tutti i ticket nello specifico ticketState
     */
     @Query("select t from Ticket  t where t.state = :ticketState")
     List<Ticket> getTicketByState(@Param("ticketState") TicketState ticketState);

     @Query("select t from Ticket t where t.resolverUser.id = :teamLeaderID")
     List<Ticket> findTicketByTeamLeaderID(@Param("teamLeaderID") Long teamLeaderID);

     @Query("select t from Ticket t where t.actualType = :category and t.target.id = :targetID")
     List<Ticket> getTicketByCategoryAndTarget(@Param("category") String category,@Param("targetID") Long targetID);


     @Query("select t from Ticket t where  t.target.id = :targetID ")
     List<Ticket> getTicketByTarget(@Param("targetID") Long targetID);
}
