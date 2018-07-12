package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TicketDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FSM.FSM;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;


@Service
public class TicketController {

    private final TicketDao ticketDao;

    private final RegisteredUserController registeredUserController;

    @Autowired
    public TicketController(TicketDao ticketDao, RegisteredUserController registeredUserController) {
        this.ticketDao = ticketDao;
        this.registeredUserController = registeredUserController;
    }

    @Transactional
    public @NotNull Ticket insertTicket(@NotNull Ticket ticket) {

        String stateMachineFileName = ticket.getTarget().getStateMachineName();

        String relativePath = "./src/main/resources/state_machine/xml_files/";

        ticket.createStateMachine( relativePath + stateMachineFileName + ".xml");

        State currentState = State.getEnum(ticket.getStateMachine().getCurrentState());
        if(currentState==null)
            return null;
        ticket.setCurrentState(currentState);
        ticket.setTTL(currentState.getTTL());
        ticket.setStateCounter(System.currentTimeMillis());
        FSM stateMachine = ticket.getStateMachine();
        ticket.setStateInformation(stateMachine.getStateInformation(currentState.toString()));
        ticket.setResolverUser(registeredUserController.getTeamCoordinator());
        ticket.setCustomerState(false);
        return ticketDao.save(ticket);
    }

    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long id, @NotNull Ticket updatedData) {

        Ticket toBeUpdatedTicket = ticketDao.getOne(id);
        toBeUpdatedTicket.updateTicket(updatedData);
        return ticketDao.save(toBeUpdatedTicket);

    }


    @Transactional
    public Ticket findTicketById(@NotNull Long id) {
        Optional<Ticket> optionalTicket = ticketDao.findById(id);
        return optionalTicket.orElse(null);
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
    public List<Ticket> getTicketByState(State ticketState){
        return ticketDao.getTicketByState(ticketState);
    }




    /**
     * Assegna un Ticket a un TeamLeader
     *
     * @param ticketID l'id del ticket da assegnare
     * @param teamLeaderID l'id del team leader a cui assegnare il ticket.
     *
     */
    @Transactional
    public void assignTicket(Long ticketID, Long teamLeaderID) {
        Ticket assignedTicket  = this.findTicketById(ticketID);
        TeamLeader teamLeader = (TeamLeader) registeredUserController.findRegisteredUserById(teamLeaderID);
        assignedTicket.setResolverUser(teamLeader);
        ticketDao.save(assignedTicket);
    }



    /**
     * Metodo per inserire un commento in un ticket
     *
     * @param ticketID ID del ticket da commentare
     * @param ticketComment commento da allegare al ticket
     * @return il ticket con allegato il commento
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
     * @param ticketID id del ticket da modificare
     * @param action String che identifica l'azione da intraprendere come configurato nell file XML.
     * @return il ticket modificato
     */
    @Transactional
    public Ticket changeState(Long ticketID, String action){

        Ticket ticket = findTicketById(ticketID);
        ticket.getStateMachine().ProcessFSM(action);
        State state = State.getEnum(ticket.getStateMachine().getCurrentState());
        if(state==null)
            return null;
        ticket.setCurrentState(state);
        ticket.setTTL(state.getTTL());
        ticket.setStateCounter(System.currentTimeMillis());
        FSM stateMachine = ticket.getStateMachine();
        ticket.setStateInformation(stateMachine.getStateInformation(state.toString()));
        return ticketDao.save(ticket);
    }

    /**
     * Metodo per cambiare lo stato di un ticket e il Resolver User del Ticket.
     *
     * @param internalUserID id dell'internal user da cambiare
     * @param ticketID id del ticket da assegnare
     * @param action String che identifica l'azione da intraprendere come configurato nell file XML.
     * @return il Ticket aggiornato con lo stato e il resolver user modificati
     */
    @Transactional
    public Ticket changeStateAndResolverUser(Long ticketID, String action, String internalUserID) {

        Ticket ticket = findTicketById(ticketID);
        RegisteredUser registeredUser = null;
        RegisteredUser exResolverUser = ticket.getResolverUser();
        ticket.setCustomerState(false);
        if(internalUserID.equals(SystemRole.TeamLeader.toString())){

            if(!exResolverUser.getClass().equals(TeamLeader.class)){

                if(exResolverUser.getClass().equals(TeamMember.class)){

                    TeamMember exTeamMember = (TeamMember) exResolverUser;
                    registeredUser = exTeamMember.getTeam().getTeamLeader();

                }
                if(exResolverUser.getClass().equals(TeamCoordinator.class)){

                    registeredUser = registeredUserController.getRandomTeamLeader();


                }
            }
            else {

                registeredUser = exResolverUser;
            }
        }
        else if(internalUserID.equals(SystemRole.TeamMember.toString())){
            if(!exResolverUser.getClass().equals(TeamMember.class)){
                if(exResolverUser.getClass().equals(TeamCoordinator.class)){
                    registeredUser = registeredUserController.getRandomTeamMember();

                }
                if(exResolverUser.getClass().equals(TeamLeader.class)){
                    registeredUser = exResolverUser;

                }
            }
            else registeredUser = exResolverUser;
        }
        else if(internalUserID.equals(SystemRole.TeamCoordinator.toString())){
            if(!exResolverUser.getClass().equals(TeamCoordinator.class)){
                registeredUser = registeredUserController.getTeamCoordinator();
            }
            else registeredUser = exResolverUser;
        }
        else {
            Long iUserID = Long.parseLong(internalUserID);
            if(iUserID != 0 ) {

                registeredUser = registeredUserController.findRegisteredUserById(iUserID);
                ticket.setResolverUser((InternalUser) registeredUser);
            }
            else {
                ticket.setCustomerState(true);
                registeredUser = exResolverUser;
            }
        }
        ticket.setResolverUser((InternalUser) registeredUser);
        ticketDao.save(ticket);

        return changeState(ticketID, action);
    }



    public Ticket updateTicketPriority(Long id, Priority priority) {

        Ticket ticket = findTicketById(id);
        ticket.setActualPriority(priority);

        return ticketDao.save(ticket);
    }

    public Ticket updateTicketPriorityAndActualType(Long id, Priority priority, String actualType) {
        Ticket ticket = findTicketById(id);
        ticket.setActualPriority(priority);
        ticket.setActualType(actualType);
        return ticketDao.save(ticket);
    }
}
