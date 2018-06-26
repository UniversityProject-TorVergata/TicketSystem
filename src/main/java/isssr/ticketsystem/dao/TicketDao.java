package isssr.ticketsystem.dao;


import isssr.ticketsystem.entity.Ticket;
import isssr.ticketsystem.enumeration.State;
import isssr.ticketsystem.enumeration.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Long> {

     /**
      * To search the ticket opened by a specified user username
      *
      * @param customerID
      * @return All tickets opened by a user
      */
     @Query("select t from Ticket t where t.openerUser.id = :customerID")
     List<Ticket> getTicketByOpenerUser(@Param("customerID") Long customerID);

     /**
      * To search the ticket assigned to a specified TeamMember username
      *
      * @param teamLeaderID
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



    /**
     * Metodo query che seleziona tutti i Ticket relativi a un determinato Target e ad una determinata Categoria
     *
     * @param category
     * @param targetID Id del Target di cui cercare i ticket.
     * @return Lista dei Ticket della Categoria e del Target in argomento.
     */
     @Query("select t from Ticket t where t.actualType = :category and t.target.id = :targetID")
     List<Ticket> getTicketByCategoryAndTarget(@Param("category") String category,@Param("targetID") Long targetID);

    /**
     * Metodo Query che seleziona i ticket relativi a un determinato Target
     *
     * @param targetID ID del target di cui sono cercati i Ticket.
     * @return la lista dei Ticket.
     */
     @Query("select t from Ticket t where  t.target.id = :targetID ")
     List<Ticket> getTicketByTarget(@Param("targetID") Long targetID);
}
