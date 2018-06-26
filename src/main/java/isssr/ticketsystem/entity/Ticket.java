package isssr.ticketsystem.entity;

import Action.FSMAction;
import FSM.FSM;
import com.fasterxml.jackson.annotation.JsonIgnore;
import isssr.ticketsystem.enumeration.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Entita' fondamentale del sistema.
 * E' aperto da un utente per richiedere assistenza su un Target da lui Posseduto
 *
 */
@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {


    @Id
    @GeneratedValue // Autoincrement
    private Long id;



    private String timestamp;

    private long stateCounter;

    private String presumedType;


    private String actualType;

    private String title;

    private String description;

    @Lob
    private String attachedFile;

    private String mediaType;

    @ManyToOne
    private InternalUser resolverUser;

    @ManyToOne
    private RegisteredUser openerUser;

    @ManyToOne
    private Target target;

    @Enumerated(EnumType.STRING)
    private Priority customerPriority;

    @Enumerated(EnumType.STRING)
    private Priority actualPriority;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;


    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;



    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<TicketComment> ticketComments;

    @ElementCollection(targetClass = TAG.class)
    @Enumerated(EnumType.STRING)
    List<TAG> tags;

    /**
     * Macchina a stati per ciascun Ticket che definisce il suo workflow.
     */
    @JsonIgnore
    @Lob
    private FSM stateMachine;

    /**
     * Stato Corrente del Ticket.
     */
    @Enumerated(EnumType.STRING)
    private State currentState;

    private int TTL;

    /**
     * Informazioni sullo stato attuale del Ticket:
     *  - Azioni.
     *  - Ruoli.
     *  - Stati successivi.
     */
    private ArrayList<ArrayList<String>> stateInformation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }


    public void setStateCounter(long stateCounter) {
        this.stateCounter = stateCounter;
    }


    public void setActualType(String actualType) {
        this.actualType = actualType;
    }


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }


    public void setActualPriority(Priority actualPriority) {
        this.actualPriority = actualPriority;
    }


    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    public List<TicketComment> getTicketComments() {
        return ticketComments;
    }


    public FSM getStateMachine() {
        return this.stateMachine;
    }



    public void setTTL(int TTL) {
        this.TTL = TTL;
    }


    public void setStateInformation(ArrayList<ArrayList<String>> stateInformation) {
        this.stateInformation = stateInformation;
    }



    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.TicketRestService
     * @param updatedData Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    public void updateTicket(Ticket updatedData) {
        this.timestamp = updatedData.timestamp;
        this.presumedType = updatedData.presumedType;
        this.actualType = updatedData.actualType;
        this.title = updatedData.title;
        this.description = updatedData.description;
        this.resolverUser = updatedData.resolverUser;
        this.openerUser = updatedData.openerUser;
        this.target = updatedData.target;
        this.customerPriority = updatedData.customerPriority;
        this.actualPriority = updatedData.actualPriority;
        this.visibility = updatedData.visibility;

        this.difficulty = updatedData.difficulty;

        this.ticketComments = updatedData.ticketComments;
        this.tags = updatedData.tags;
    }

    public List<TAG> getTags() {
        return tags;
    }



    public void setResolverUser(InternalUser resolverUser) {
        this.resolverUser = resolverUser;
    }



    public void createStateMachine(String fileXMLStates) {

        try {
            this.stateMachine = new FSM(fileXMLStates, new FSMAction() {
                @Override
                public boolean action(String curState, String message, String nextState, Object args) {
                    System.out.println(curState + ":" + message + " : " + nextState);

                    // Here we can implement our login of how we wish to handle
                    // an action

                    return true;
                }
            });
        }
        catch (Exception e){
            System.out.println("Error\n" + e);
        }
    }
}
