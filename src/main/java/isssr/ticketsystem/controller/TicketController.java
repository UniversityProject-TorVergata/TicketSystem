package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TicketDao;
import isssr.ticketsystem.entity.Ticket;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Transactional
    public @NotNull Ticket insertCompany(@NotNull Ticket company) {
        Ticket createdTicket = ticketDao.save(company);
        return createdTicket;
    }

    @Transactional
    public @NotNull Ticket updateCompany(@NotNull Long id, @NotNull Ticket updatedData) throws NotFoundEntityException {

        Ticket toBeUpdatedTicket = ticketDao.getOne(id);

        if (toBeUpdatedTicket == null)
            throw new NotFoundEntityException();

        toBeUpdatedTicket.updateTicket(updatedData);
        Ticket updatedTicket = ticketDao.save(toBeUpdatedTicket);

        return toBeUpdatedTicket;
    }

    public Ticket findCompanyById(@NotNull Long id) {
        Ticket foundTicket = ticketDao.getOne(id);
        return foundTicket;
    }

    public boolean deleteCompany(@NotNull Long id) {
        if (!ticketDao.existsById(id)) {
            return false;
        }
        ticketDao.deleteById(id);
        return true;
    }

    public List<Ticket> getTicket() {

        return ticketDao.findAll();
    }
}
