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


@RestController
@RequestMapping(path = "ticket")
public class TicketRestService {

    @Autowired
    private TicketController ticketController;
    @Autowired
    private RegisteredUserController registeredUserController;
    @Autowired
    private TargetController targetController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Ticket> insertTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketController.insertTicket(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

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

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> findTicket(@PathVariable Long id) {
        Ticket ticketFound = ticketController.findTicketById(id);
        return new ResponseEntity<>(ticketFound, ticketFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Boolean> deleteTicket(@PathVariable("id") String id) {
        boolean deletedTicket = ticketController.deleteTicket(Long.parseLong(id));
        return new ResponseEntity<>(deletedTicket, deletedTicket ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTickets() {
        List<Ticket> tickets = ticketController.getTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path="/ticketByOpenerUser",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByOpenerUser(@RequestParam("username") String username){

        List<Ticket> tickets = ticketController.getTicketByOpenerUser(username);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }

    @RequestMapping(path="/ticketByResolverUser",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketsByResolverUser(@RequestParam("username") String username){


        List<Ticket> tickets = ticketController.getTicketByOpenerUser(username);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }

    @RequestMapping(path = "/findTicket",method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> getTicketByAll(@RequestParam("searchType") int searchType){


        return null;
    }

    @RequestMapping(path = "/findTicketByState/{state}",method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getTicketByState(@PathVariable("state") TicketState ticketState){
        Log logger = LogFactory.getLog(getClass());
        logger.info(ticketState.toString() +" " + ticketState.getClass());
        List<Ticket> tickets = ticketController.getTicketByState(ticketState);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }


    @RequestMapping(path= "/assignTicket/{ticketID}/{teamLeaderID}",method = RequestMethod.PUT)
    public ResponseEntity<Ticket> assignTicket(@PathVariable("ticketID") Long ticketID,@PathVariable("teamLeaderID") Long teamLeaderID)
            throws NotFoundEntityException {

        ticketController.assignTicket(ticketID,teamLeaderID);
        Ticket ticket = ticketController.findTicketById(ticketID);
        return new ResponseEntity<>(ticket,HttpStatus.OK);

    }

}

