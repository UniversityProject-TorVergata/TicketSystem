package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TicketDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.Difficulty;
import isssr.ticketsystem.enumeration.Priority;
import isssr.ticketsystem.enumeration.State;
import isssr.ticketsystem.enumeration.TAG;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RegisteredUserController registeredUserController;

    @Transactional
    public @NotNull Ticket insertTicket(@NotNull Ticket ticket) {

        String stateMachineFileName = ticket.getTarget().getStateMachineName();

        String relativePath = "./src/main/java/isssr/ticketsystem/state_machine/xml_files/";

        ticket.createStateMachine( relativePath + stateMachineFileName + ".xml");

        State currentState = State.getEnum(ticket.getStateMachine().getCurrentState());
        ticket.setCurrentState(currentState);
        ticket.setTTL(currentState.getTTL());
        ticket.setStateCounter(System.currentTimeMillis());
        Ticket createdTicket = ticketDao.save(ticket);
        return createdTicket;
    }

    // TODO perche "Action1" statica?
    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long id, @NotNull Ticket updatedData) throws NotFoundEntityException {

        Ticket toBeUpdatedTicket = ticketDao.getOne(id);

        if (toBeUpdatedTicket == null)
            throw new NotFoundEntityException();

        toBeUpdatedTicket.updateTicket(updatedData);
        //toBeUpdatedTicket.getStateMachine().ProcessFSM("Action1");
        //toBeUpdatedTicket.setCurrentState(toBeUpdatedTicket.getStateMachine().getCurrentState());
        Ticket updatedTicket = ticketDao.save(toBeUpdatedTicket);

        return updatedTicket;
    }


    @Transactional
    public Ticket findTicketById(@NotNull Long id) {
        Optional<Ticket> optionalTicket = ticketDao.findById(id);
        return optionalTicket.get();
    }

    @Transactional
    public boolean deleteTicket(@NotNull Long id) {
        if (!ticketDao.existsById(id)) {
            return false;
        }
        ticketDao.deleteById(id);
        return true;
    }

    @Transactional
    public List<Ticket> getTickets() {

        return ticketDao.findAll();
    }

    @Transactional
    public List<Ticket> getTicketByOpenerUser(Long customerID){

        return ticketDao.getTicketByOpenerUser(customerID);
    }

    @Transactional
    public List<Ticket> getTicketByResolverUser(Long teamLeaderID){

        return ticketDao.getTicketByResolverUser(teamLeaderID);
    }


    @Transactional
    public List<Ticket> getTicketByState(String ticketState){

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
        ticketDao.save(assignedTicket);
    }

    @Transactional
    public List<Ticket> findTicketByTeamLeaderID(Long teamLeaderID) {
        return ticketDao.findTicketByTeamLeaderID(teamLeaderID);

    }

    /**
     * Ricerca "ESCLUSIVA" di un Ticket dati un Target,una Categoria e una lista di TAG.
     * Esclusiva : Il ticket deve contenere almeno tutti i Tag in argomento.
     *
     * @param category
     * @param tags
     * @param targetID
     * @return
     */
    @Transactional
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
     * Inclusiva : Il ticket deve contenere almeno uno dei Tag in argomento.
     *
     * @param category
     * @param tags
     * @param targetID
     * @return
     */
    @Transactional
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
    @Transactional
    public List<Ticket> findTicketByTarget(Long targetID){
        return ticketDao.getTicketByTarget(targetID);
    }

    /**
     * Metodo per inserire un commento in un ticket
     *
     * @param ticketID ID del ticket da commentare
     * @param ticketComment commento da allegare al ticket
     * @return
     */
    @Transactional
    public Ticket insertComment(Long ticketID, TicketComment ticketComment) {

        Ticket ticket = findTicketById(ticketID);
        if(ticket!=null){
            ticket.getTicketComments().add(ticketComment);
            ticketDao.save(ticket);
        }

        return ticket;
    }

    public Ticket updateTicketDifficulty(Long id,Difficulty difficulty) {

        Ticket ticket = findTicketById(id);
        ticket.setDifficulty(difficulty);

        return ticketDao.save(ticket);
    }

    /**
     * Metodo per cambiare lo stato di un ticket.
     *
     *
     * @param ticketID
     * @param action String che identifica l'azione da intraprendere come configurato nell file XML.
     * @return
     */
    @Transactional
    public Ticket changeState(Long ticketID, String action){

        Ticket ticket = findTicketById(ticketID);
        ticket.getStateMachine().ProcessFSM(action);
        State state = State.getEnum(ticket.getStateMachine().getCurrentState());
        ticket.setCurrentState(state);
        ticket.setTTL(state.getTTL());
        ticket.setStateCounter(System.currentTimeMillis());
        return ticketDao.save(ticket);
    }

    /**
     * Metodo per cambiare lo stato di un ticket e il Resolver User del Ticket.
     *
     *
     * @param ticketID
     * @param action String che identifica l'azione da intraprendere come configurato nell file XML.
     * @return
     */
    @Transactional
    public Ticket changeStateAndResolverUser(Long ticketID, String action, Long internalUserID) {

        Ticket ticket = findTicketById(ticketID);
        RegisteredUser registeredUser = registeredUserController.findRegisteredUserById(internalUserID);
        ticket.setResolverUser((InternalUser) registeredUser);
        ticketDao.save(ticket);

        return changeState(ticketID, action);
    }

    @Transactional
    public Ticket changeStateResolverUserPriorityAndType(Long ticketID, String action, Long internalUserID, Priority priority, String actualType) {

        Ticket ticket = findTicketById(ticketID);
        RegisteredUser registeredUser = registeredUserController.findRegisteredUserById(internalUserID);
        ticket.setResolverUser((InternalUser) registeredUser);
        ticket.setActualPriority(priority);
        ticket.setActualType(actualType);
        ticketDao.save(ticket);

        return changeState(ticketID, action);
    }

}
