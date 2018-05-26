package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TicketDao;
import isssr.ticketsystem.entity.TAG;
import isssr.ticketsystem.entity.Ticket;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Transactional
    public @NotNull Ticket insertTicket(@NotNull Ticket ticket) {
        Ticket createdTicket = ticketDao.save(ticket);
        return createdTicket;
    }

    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long id, @NotNull Ticket updatedData) throws NotFoundEntityException {

        Ticket toBeUpdatedTicket = ticketDao.getOne(id);

        if (toBeUpdatedTicket == null)
            throw new NotFoundEntityException();

        toBeUpdatedTicket.updateTicket(updatedData);
        Ticket updatedTicket = ticketDao.save(toBeUpdatedTicket);

        return updatedTicket;
    }

    public Ticket findTicketById(@NotNull Long id) {
        Ticket foundTicket = ticketDao.getOne(id);
        return foundTicket;
    }

    public boolean deleteTicket(@NotNull Long id) {
        if (!ticketDao.existsById(id)) {
            return false;
        }
        ticketDao.deleteById(id);
        return true;
    }

    public List<Ticket> getTickets() {

        return ticketDao.findAll();
    }

    public List<Ticket> getTicketByOpenerUser(String username){

        return ticketDao.getTicketByOpenerUser(username);
    }

    public List<Ticket> getTicketByResolverUser(String username){

        return ticketDao.getTicketByResolverUser(username);
    }

    public List<Ticket> getTicketByTag(List<TAG> tags){

        List<Ticket> allTicket = ticketDao.findAll();
        for(Ticket t : allTicket){
            List<TAG> tTag = t.getTags();
            for(TAG tag : tags){
                if(tTag.contains(tag))
                    continue;
                else
                    allTicket.remove(t);
            }
        }
        return allTicket;

    }
}
