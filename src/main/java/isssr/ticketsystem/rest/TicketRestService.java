package isssr.ticketsystem.rest;



import isssr.ticketsystem.controller.TicketController;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.Difficulty;
import isssr.ticketsystem.enumeration.Priority;
import isssr.ticketsystem.enumeration.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("ticket")
 * attraverso i metodi definiti nella classe TicketController.
 */


@RestController
@RequestMapping(path = "ticket")
@SuppressWarnings("ConstantConditions")
public class TicketRestService {

    private final TicketController ticketController;

    @Autowired
    public TicketRestService(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il ticket viene inserito nel DB.
     * @param ticket che va aggiunto al DB.
     * @return info del ticket aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Ticket> insertTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketController.insertTicket(ticket);
        if(createdTicket != null)
            return new ResponseEntity<>(createdTicket, HttpStatus.OK);
        else
            return new ResponseEntity<>(createdTicket, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo le info relative al ticket specificato
     * vengono aggiornate nel DB.
     * @param id Id del ticket da aggiornare.
     * @param ticket aggiornato le cui info aggiornate vanno inserite nel DB.
     * @return ticket con le info aggiornate + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketController.updateTicket(id, ticket);
        if(updatedTicket != null)
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTicket, HttpStatus.NOT_FOUND);
    }


    /**
     * Metodo usato per cambiare il campo "difficulty" del Ticket
     * @param id Id del ticket da aggiornare
     * @param difficulty Nuova difficoltà del Ticket
     * @return lista dei TeamMember senza Team
     */
    @RequestMapping(path = "changeDifficulty/{difficulty}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> updateTicketDifficulty(@PathVariable("id") Long id, @PathVariable("difficulty") Difficulty difficulty) {
        Ticket updatedTicket;
        updatedTicket = ticketController.updateTicketDifficulty(id, difficulty);
        if(updatedTicket != null)
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTicket, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per cambiare il campo "difficulty" del Ticket
     * @param id Id del ticket da aggiornare
     * @param priority Nuova difficoltà del Ticket
     * @return lista dei TeamMember senza Team
     */
    @RequestMapping(path = "changePriority/{priority}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> updateTicketPriority(@PathVariable("id") Long id, @PathVariable("priority") Priority priority) {
        Ticket updatedTicket;
        updatedTicket = ticketController.updateTicketPriority(id, priority);
        if(updatedTicket != null)
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTicket, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per cambiare priority e tipo di un ticket
     *
     * @param id Id del ticket da aggiornare
     * @param priority Nuova difficoltà del Ticket
     * @param actualType tipo "actual" del ticket
     * @return lista dei TeamMember senza Team
     */
    @RequestMapping(path = "changePriorityAndType/{priority}/{actualType}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> updateTicketPriorityAndActualType(@PathVariable("id") Long id, @PathVariable("priority") Priority priority,@PathVariable("actualType") String actualType) {
        Ticket updatedTicket;
        updatedTicket = ticketController.updateTicketPriorityAndActualType(id, priority,actualType);
        if(updatedTicket != null)
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTicket, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo per spostare il ticket tra gli stati della FSM.
     *
     * @param ticketID ID del ticket di cui deve essere cambiato lo stato
     * @param action Azione che attiva la transizione di stato
     * @param internalUserID ID del nuovo ResolverUser
     * @return il ticket aggiornato.
     */
    @RequestMapping(path = "/changeState/{ticketID}/{action}/{internalUserID}",method = RequestMethod.POST)
    public ResponseEntity<Ticket> changeTicketStateAndResolverUser(@PathVariable("ticketID") Long ticketID, @PathVariable("action") String action,
                                             @PathVariable("internalUserID") String internalUserID){
        Ticket updatedTicket = ticketController.changeStateAndResolverUser(ticketID,action,internalUserID);
        if(updatedTicket != null)
            return new ResponseEntity<>(updatedTicket,HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTicket,HttpStatus.NOT_FOUND);
    }




    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB aperti dall'utente indicato.
     * @param customerID ID dell'utente di cui si vogliono visualizzare i ticket aperti.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path="/ticketByOpenerUser/{customerID}",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByOpenerUser(@PathVariable("customerID") Long customerID){

        List<Ticket> tickets = ticketController.getTicketByOpenerUser(customerID);
        if(tickets != null)
            return new ResponseEntity<>(tickets,HttpStatus.OK);
        else
            return new ResponseEntity<>(tickets,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB risolti dall'utente indicato.
     * @param teamLeaderID teamLeader dell'utente di cui si vogliono visualizzare i ticket risolti.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path="/ticketByResolverUser/{teamLeaderID}",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByResolverUser(@PathVariable("teamLeaderID") Long teamLeaderID){


        List<Ticket> tickets = ticketController.getTicketByResolverUser(teamLeaderID);
        if(tickets != null)
            return new ResponseEntity<>(tickets,HttpStatus.OK);
        else
            return new ResponseEntity<>(tickets,HttpStatus.NOT_FOUND);
    }


    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB corrispondenti
     * allo stato indicato.
     * @param ticketState Tipo di stato per cui si vogliono visualizzare i ticket.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "/findTicketByState/{state}",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketByState(@PathVariable("state")State ticketState){
        List<Ticket> tickets = ticketController.getTicketByState(ticketState);
        if(tickets != null)
           return new ResponseEntity<>(tickets,HttpStatus.OK);
        else
            return new ResponseEntity<>(tickets,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo viene aggiornato il Team Leader del ticket specificato.
     * @param ticketID Id del ticket di cui bisogna modificare il Team Leader.
     * @param teamLeaderID Id del Team Leader da assegnare al ticket specificato.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path= "/assignTicket/{ticketID}/{teamLeaderID}",method = RequestMethod.PUT)
    public ResponseEntity<Ticket> assignTicket(@PathVariable("ticketID") Long ticketID,@PathVariable("teamLeaderID") Long teamLeaderID) {
        ticketController.assignTicket(ticketID,teamLeaderID);
        Ticket ticket = ticketController.findTicketById(ticketID);
        if(ticket != null)
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        else
            return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);

    }





    /**
     * Servizio REST per l'inserimento di un commento in ticket.
     *
     *
     * @param ticketID ID del ticket da commentare
     * @param ticketComment commento da allegare al ticket.
     * @return il ticket con allegato il commento.
     */
    @RequestMapping(path= "/insertComment/{ticketID}",method = RequestMethod.POST)
    public ResponseEntity<Ticket> insertComment(@PathVariable("ticketID") Long ticketID,@RequestBody TicketComment ticketComment){
        Ticket commentedTicket = ticketController.insertComment(ticketID,ticketComment);
        if(commentedTicket != null)
            return new ResponseEntity<>(commentedTicket,HttpStatus.OK);
        else
            return new ResponseEntity<>(commentedTicket,HttpStatus.NOT_FOUND);
    }


}

