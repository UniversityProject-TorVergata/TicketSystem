package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.TargetController;
import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.controller.TicketController;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import lombok.NoArgsConstructor;
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
     * una richiesta di questo tipo il ticket specificato viene marcato come TRASHED ("CESTINATO") nel DB.
     * @param id Id del ticket che va cancellato dal DB.
     * @return ticket cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TicketController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Ticket> deleteTicket(@PathVariable("id") String id) {
        Ticket trashedTicket = ticketController.trashTicket(Long.parseLong(id));
        if(trashedTicket!=null)
            return new ResponseEntity<>(trashedTicket,HttpStatus.OK);
        else
            return new ResponseEntity<>(trashedTicket,HttpStatus.NOT_FOUND);
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
        if(tickets !=null)
            return new ResponseEntity<>(tickets,HttpStatus.OK);
        else
            return new ResponseEntity<>(tickets,HttpStatus.NOT_FOUND);
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
        if(tickets !=null)
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
        if(tickets !=null)
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
    //  TODO Da rifare.
    //@RequestMapping(path = "/findTicketByState/{state}",method = RequestMethod.GET)
    //public ResponseEntity<List<Ticket>> getTicketByState(@PathVariable("state") TicketState ticketState){
    //    Log logger = LogFactory.getLog(getClass());
    //    logger.info(ticketState.toString() +" " + ticketState.getClass());
    //    List<Ticket> tickets = ticketController.getTicketByState(ticketState);
    //    if(tickets !=null)
    //       return new ResponseEntity<>(tickets,HttpStatus.OK);
    //    else
    //        return new ResponseEntity<>(tickets,HttpStatus.NOT_FOUND);
    //}

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
    public ResponseEntity<List<Ticket>> findTicketByTeamLeaderID(@PathVariable("teamLeaderID") Long teamLeaderID){
        List<Ticket> ticketList = ticketController.findTicketByTeamLeaderID(teamLeaderID);
        if(ticketList !=null)
            return new ResponseEntity<>(ticketList,HttpStatus.OK);
        else
            return new ResponseEntity<>(ticketList,HttpStatus.NOT_FOUND);

    }

    /**
     * Ricerca "Esclusiva" di Ticket dati il Target e/o la Categoria e/o una lista di TAG.
     * Esclusiva significa che se indicata la lista di TAG i Ticket restituiti dovranno contenere
     * almeno tutti i tag in Argomento.
     *
     * @param searchBean Un oggetto che contiene i parametri category,targetID e tags.
     * @return Lista di Ticket trovati applicando i criteri indicati.
     */
    @RequestMapping(path = "/searchTicketExclusive",method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> searchTicketExclusive(@RequestBody SearchBean searchBean){
        List<Ticket> ticketList = ticketController.searchTicketExclusive(searchBean.getCategory(),searchBean.getTags(),searchBean.getTargetID());
        if(ticketList !=null)
            return new ResponseEntity<>(ticketList,HttpStatus.OK);
        else
            return new ResponseEntity<>(ticketList,HttpStatus.NOT_FOUND);
    }

    /**
     * Ricerca "Inclusiva" di Ticket dati il Target e/o la Categoria e/o una lista di TAG.
     * Inclusiva significa che se indicata la lista di TAG i Ticket restituiti dovranno contenere
     * almeno uno dei  tag in Argomento.
     *
     * @param searchBean Un oggetto che contiene i parametri category,targetID e tags.
     * @return Lista di Ticket trovati applicando i criteri indicati.
     */
    @RequestMapping(path = "/searchTicketInclusive",method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> searchTicketInclusive(@RequestBody SearchBean searchBean){
        List<Ticket> ticketList = ticketController.searchTicketInclusive(searchBean.getCategory(),searchBean.getTags(),searchBean.getTargetID());
        if(ticketList !=null)
            return new ResponseEntity<>(ticketList,HttpStatus.OK);
        else
            return new ResponseEntity<>(ticketList,HttpStatus.NOT_FOUND);
    }

    /**
     * Questa classe interna è usata per passare i parametri delle chiamate rest /searchTicketExclusive ,/searchTicketInclusive
     * che filtrano i ticket attraverso tag,targetID e category.
     *
     * Corrisponde ad un JSON del tipo :
     *
     * {
     *     "tags" : ["tag1","tag2","tag3"],
     *     "targetID" : 148,
     *     "category" : "query"
     * }
     *
     */
    @NoArgsConstructor
    public static class SearchBean {

        private List<TAG> tags;
        private Long targetID;
        private String category;


        public SearchBean(List<TAG> tags, Long targetID, String category) {
            this.tags = tags;
            this.targetID = targetID;
            this.category = category;
        }

        public SearchBean(List<TAG> tags, Long targetID) {
            this.tags = tags;
            this.targetID = targetID;
        }

        public SearchBean(Long targetID, String category) {
            this.targetID = targetID;
            this.category = category;
        }

        public List<TAG> getTags() {
            return tags;
        }

        public void setTags(List<TAG> tags) {
            this.tags = tags;
        }

        public Long getTargetID() {
            return targetID;
        }

        public void setTargetID(Long targetID) {
            this.targetID = targetID;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}

