package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.TargetController;
import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.controller.TicketController;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
public class TicketRestService {

    @Autowired
    private TicketController ticketController;
    @Autowired
    private RegisteredUserController registeredUserController;
    @Autowired
    private TargetController targetController;

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
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
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
        Ticket updatedTicket;
        try {
            updatedTicket = ticketController.updateTicket(id, ticket);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restituite le info relative al ticket desiderato.
     * @param id Id del ticket da visualizzare.
     * @return ticket da visualizzare + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> findTicket(@PathVariable Long id) {
        Ticket ticketFound = ticketController.findTicketById(id);
        return new ResponseEntity<>(ticketFound, ticketFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    /**
     * Metodo usato per la gestione di una Delete che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il ticket specificato viene cancellato dal DB.
     * @param id Id del ticket che va cancellato dal DB.
     * @return ticket cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Boolean> deleteTicket(@PathVariable("id") String id) {
        boolean deletedTicket = ticketController.deleteTicket(Long.parseLong(id));
        return new ResponseEntity<>(deletedTicket, deletedTicket ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restuiti tutti i ticket registrati
     * presenti nel DB.
     * @return Ticket presenti nel DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTickets() {
        List<Ticket> tickets = ticketController.getTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB aperti dall'utente indicato.
     * @param username Username dell'utente di cui si vogliono visualizzare i ticket aperti.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path="/ticketByOpenerUser",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByOpenerUser(@RequestParam("username") String username){

        List<Ticket> tickets = ticketController.getTicketByOpenerUser(username);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB risolti dall'utente indicato.
     * @param username Username dell'utente di cui si vogliono visualizzare i ticket risolti.
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path="/ticketByResolverUser",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByResolverUser(@RequestParam("username") String username){


        List<Ticket> tickets = ticketController.getTicketByOpenerUser(username);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restiutiti tutti i ticket presenti nel DB corrispondenti al tipo indicato.
     * @param searchType Tipo di ticket che si vuole visualizzare
     * @return Ticket presenti nel DB che rispondono ai criteri sopraspecificati + esito della richiesta HTTP
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "/findTicket",method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> getTicketByAll(@RequestParam("searchType") int searchType){


        return null;
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
    public ResponseEntity<List<Ticket>> getTicketByState(@PathVariable("state") TicketState ticketState){
        Log logger = LogFactory.getLog(getClass());
        logger.info(ticketState.toString() +" " + ticketState.getClass());
        List<Ticket> tickets = ticketController.getTicketByState(ticketState);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
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
    public ResponseEntity<Ticket> assignTicket(@PathVariable("ticketID") Long ticketID,@PathVariable("teamLeaderID") Long teamLeaderID)
            throws NotFoundEntityException {

        ticketController.assignTicket(ticketID,teamLeaderID);
        Ticket ticket = ticketController.findTicketById(ticketID);
        return new ResponseEntity<>(ticket,HttpStatus.OK);

    }

    /**
     * Questo metodo ricerca i ticket assegnati a un Determinato TeamLeader
     *
     * @param teamLeaderID ID del team leader su cui si effettua le ricerca
     * @return una lista con i ticket assegnati al TeamLeader ricercato.
     */
    @RequestMapping(path = "/findTicketByTeamLeader/{teamLeaderID}",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketByState(@PathVariable("teamLeaderID") Long teamLeaderID){
        List<Ticket> ticketList = ticketController.findTicketByTeamLeaderID(teamLeaderID);
        return new ResponseEntity<>(ticketList,HttpStatus.OK);

    }

}

