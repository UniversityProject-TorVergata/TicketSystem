package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TicketDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.apache.tomcat.util.descriptor.tld.TldRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RegisteredUserController registeredUserController;

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


    @Transactional
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

    public List<Ticket> getTicketByOpenerUser(Long customerID){

        return ticketDao.getTicketByOpenerUser(customerID);
    }

    public List<Ticket> getTicketByResolverUser(Long teamLeaderID){

        return ticketDao.getTicketByResolverUser(teamLeaderID);
    }

    @Transactional
    public List<Ticket> getTicketByState(TicketState ticketState){

        List<Ticket> tickets = ticketDao.getTicketByState(ticketState);

        return ticketDao.getTicketByState(ticketState);
    }

    /**
     * Metodo "interno" depura una Lista di Ticket eliminando quelli che non contengono tutti i Tag passati in argomento.
     *
     * @param tickets
     * @param tags TAG su cui filtrare i ticket.
     * @return
     */
    private List<Ticket> searchTicketTagExclusive(List<Ticket> tickets ,List<TAG> tags){
        List<Ticket>  outputList = new ArrayList<>();
        outputList.addAll(tickets);
        for(Ticket t : tickets){
            List<TAG> tTag = t.getTags();
            for(TAG tag : tags){
                if(tTag.contains(tag))
                    continue;
                else
                    outputList.remove(t);
            }
        }
        return outputList;

    }

    /**
     * Metodo interno che depura una lista di Ticket eliminando quelli che non contengono almeno uno dei Tag passati in argomento.
     *
     * @param tickets
     * @param tags TAG su cui filtrare i Ticket.
     * @return
     */
    private List<Ticket> searchTicketTagInclusive(List<Ticket> tickets ,List<TAG> tags){
        List<Ticket>  outputList = new ArrayList<>();
        outputList.addAll(tickets);

        for(Ticket t : tickets){
            boolean find = false;
            List<TAG> tTag = t.getTags();
            for(TAG tag : tags) {
                if (tTag.contains(tag))
                    find = true;
                    break;


            }
            if(find == false)
                outputList.remove(t);
        }
        return outputList;

    }

    /**
     * Assegna un Ticket a un TeamLeader
     *
     * @param ticketID
     * @param teamLeaderID
     * @throws NotFoundEntityException
     */
    @Transactional
    public void assignTicket(Long ticketID, Long teamLeaderID) throws NotFoundEntityException {
        Ticket assignedTicket  = this.findTicketById(ticketID);
        TeamLeader teamLeader = (TeamLeader) registeredUserController.findRegisteredUserById(teamLeaderID);
        assignedTicket.setResolverUser(teamLeader);
        assignedTicket.setState(TicketState.READY);
        ticketDao.save(assignedTicket);
    }

    public List<Ticket> findTicketByTeamLeaderID(Long teamLeaderID) {
        return ticketDao.findTicketByTeamLeaderID(teamLeaderID);

    }

    /**
     * Ricerca "ESCLUSIVA" di un Ticket dati un Target,una Categoria e una lista di TAG.
     * Esclusiva --> Il ticket deve contenere almeno tutti i Tag in argomento.
     *
     * @param category
     * @param tags
     * @param targetID
     * @return
     */
    public List<Ticket> searchTicketExclusive(String category, List<TAG> tags, Long targetID) {

        if(tags == null){
            //Ricerca Prodotto,Categoria
            return ticketDao.getTicketByCategoryAndTarget(category,targetID);
        }
        else if(category == null){
            //Ricerca Prodotto,TAG
           List<Ticket> ticketList = findTicketByTarget(targetID);
           return searchTicketTagExclusive(ticketList,tags);
        }
        else {
            //Ricerca Prodotto,TAG,Categoria
            List<Ticket> ticketList = ticketDao.getTicketByCategoryAndTarget(category,targetID);

            return  searchTicketTagExclusive(ticketList,tags);
        }
    }

    /**
     * Ricerca "INCLUSIVA" di un Ticket dati un Target,una Categoria e una lista di TAG.
     * Inclusiva --> Il ticket deve contenere almeno uno dei Tag in argomento.
     *
     * @param category
     * @param tags
     * @param targetID
     * @return
     */
    public List<Ticket> searchTicketInclusive(String category, List<TAG> tags, Long targetID) {

        if(tags == null){
            //Ricerca Prodotto,Categoria
            return ticketDao.getTicketByCategoryAndTarget(category,targetID);
        }
        else if(category == null){
            //Ricerca Prodotto,TAG
            List<Ticket> ticketList = findTicketByTarget(targetID);
            return searchTicketTagInclusive(ticketList,tags);
        }
        else {
            //Ricerca Prodotto,TAG,Categoria
            List<Ticket> ticketList = ticketDao.getTicketByCategoryAndTarget(category,targetID);

            return  searchTicketTagInclusive(ticketList,tags);
        }
    }

    /**
     * Ricerca i Ticket relativi ad un determinato target.
     *
     * @param targetID ID del target di cui cercare i ticket.
     * @return La lista di ticket collegati al target in argomento.
     */
    public List<Ticket> findTicketByTarget(Long targetID){
        return ticketDao.getTicketByTarget(targetID);
    }

    /**
     * "Cestina" un ticket ponendo il suo stato a TRASHED.
     *
     * @param ticketID ID del Ticket da cestinare.
     * @return Il ticket con lo stato aggiornato.
     */
    public Ticket trashTicket(Long ticketID) {
        Ticket ticket = findTicketById(ticketID);
        ticket.setState(TicketState.TRASHED);
        return ticketDao.save(ticket);


    }
}
