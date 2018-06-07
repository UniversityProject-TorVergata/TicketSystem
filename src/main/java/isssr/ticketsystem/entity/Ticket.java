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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


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

    @Enumerated(EnumType.STRING)
    private UserType sourceType;

    private String timestamp;


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

    @OneToMany
    private Map<RelationshipType,RelatedTicket> relationships;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;


    @OneToMany
    private List<SystemEvent> eventRegister;

    @OneToMany(targetEntity = SystemEvent.class )
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<TicketComment> ticketComments;

    @JsonIgnore
    private String currentState;

    @ElementCollection(targetClass = TAG.class)
    @Enumerated(EnumType.STRING)
    List<TAG> tags;

    /**
     * Macchina a stati per ciascun Ticket che definisce il suo workflow.
     */
    @Lob
    private FSM stateMachine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getSourceType() {
        return sourceType;
    }

    public void setSourceType(UserType sourceType) {
        this.sourceType = sourceType;
    }

    @JsonIgnore
    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPresumedType() {
        return presumedType;
    }

    public void setPresumedType(String presumedType) {
        this.presumedType = presumedType;
    }

    public String getActualType() {
        return actualType;
    }

    public void setActualType(String actualType) {
        this.actualType = actualType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public RegisteredUser getOpenerUser() {
        return openerUser;
    }

    public void setOpenerUser(RegisteredUser openerUser) {
        this.openerUser = openerUser;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Priority getCustomerPriority() {
        return customerPriority;
    }

    public void setCustomerPriority(Priority customerPriority) {
        this.customerPriority = customerPriority;
    }

    public Priority getActualPriority() {
        return actualPriority;
    }

    public void setActualPriority(Priority actualPriority) {
        this.actualPriority = actualPriority;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Map<RelationshipType, RelatedTicket> getRelationships() {
        return relationships;
    }

    public void setRelationships(Map<RelationshipType, RelatedTicket> relationships) {
        this.relationships = relationships;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<SystemEvent> getEventRegister() {
        return eventRegister;
    }

    public void setEventRegister(List<SystemEvent> eventRegister) {
        this.eventRegister = eventRegister;
    }

    public List<TicketComment> getTicketComments() {
        return ticketComments;
    }

    public void setTicketComments(List<TicketComment> ticketComments) {
        this.ticketComments = ticketComments;
    }

    public FSM getStateMachine() {
        return this.stateMachine;
    }

    //Costruttore usato per la CRUD utente.
    public Ticket(UserType sourceType ,
                  String presumedType, String title,
                  String description, String attachedFile,
                  String attachedByteStream, String attachedByteStreamType,
                  TeamMember resolverUser, RegisteredUser openerUser, Target target, Priority customerPriority,
                  Visibility visibility) {
        this.sourceType = sourceType;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.presumedType = presumedType;
        this.title = title;
        this.description = description;
        //TODO aggiungere i campi per l'allegato
        this.attachedFile = attachedFile;
        //this.attachedByteStream = attachedByteStream;
        //this.attachedByteStreamType = attachedByteStreamType;
        this.resolverUser = resolverUser;
        this.openerUser = openerUser;
        this.target = target;
        this.customerPriority = customerPriority;
        this.visibility = visibility;

    }

    /**
     * Costruttore del Ticket da usare per l'apertura del ticket
     *
     * @param sourceType tipo di utente che apre il sistema.
     * @param timestamp
     * @param presumedType tipo di problema indicato dall'utente.
     * @param title
     * @param description
     * @param attachedFile file allegato al ticket.
     * @param mediaType formato del file allegato al ticket.
     * @param openerUser l'utente che apre il ticket.
     * @param target Target per il quale si richiede assistenza nel ticket.
     * @param customerPriority priorità assegnata dall'utente al ticket.
     * @param visibility visibilità del ticket agli altri Customer.
     * @param tags Elenco di tag indicati dall utente all'apertura del ticket.
     */
    public Ticket( UserType sourceType, String timestamp, String presumedType,
                  String title, String description, String attachedFile, String mediaType,
                  RegisteredUser openerUser, Target target, Priority customerPriority, Visibility visibility,
                   List<TAG> tags) {
        this.sourceType = sourceType;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.presumedType = presumedType;
        this.title = title;
        this.description = description;
        //TODO ATTACHMENT DI UN FILE.
        this.attachedFile = attachedFile;
        this.mediaType = mediaType;
        this.openerUser = openerUser;
        this.target = target;
        this.customerPriority = customerPriority;
        this.visibility = visibility;
        this.tags = tags;
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.TicketRestService
     * @param updatedData Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    public void updateTicket(Ticket updatedData) {

        this.sourceType = updatedData.sourceType;
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
        this.relationships = updatedData.relationships;
        this.difficulty = updatedData.difficulty;
        this.eventRegister = updatedData.eventRegister;
        this.ticketComments = updatedData.ticketComments;
        this.tags = updatedData.tags;
        //TODO aggiungere i campi per l allegato
        //this.attachedByteStream = updatedData.attachedByteStream;
        //this.attachedByteStreamType = updatedData.attachedByteStreamType;
    }

    public List<TAG> getTags() {
        return tags;
    }

    public void setTags(List<TAG> tags) {
        this.tags = tags;
    }

    public InternalUser getResolverUser() {
        return resolverUser;
    }

    public void setResolverUser(InternalUser resolverUser) {
        this.resolverUser = resolverUser;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", sourceType=" + sourceType +
                ", timestamp='" + timestamp + '\'' +
                ", presumedType=" + presumedType +
                ", actualType=" + actualType +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", attachedFile='" + attachedFile + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", resolverUser=" + resolverUser +
                ", openerUser=" + openerUser +
                ", target=" + target +
                ", customerPriority=" + customerPriority +
                ", actualPriority=" + actualPriority +
                ", visibility=" + visibility +
                ", relationships=" + relationships +
                ", difficulty=" + difficulty +
                ", eventRegister=" + eventRegister +
                ", ticketComments=" + ticketComments +
                ", tags=" + tags +
                '}';
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
