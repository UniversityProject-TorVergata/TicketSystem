package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.ProductController;
import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.controller.TicketController;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProductController productController;

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
}

