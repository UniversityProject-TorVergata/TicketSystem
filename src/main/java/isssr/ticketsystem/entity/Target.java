package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;


/**
 * Il Target e' l'entita' per la quale il sistema di Ticketing offre assistenza.
 * Puo' essere un Prodotto o un Servizio.
 *
 */
@Entity
@Table(name = "target")
@Getter
@Setter
public class Target {

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    @Enumerated(EnumType.STRING)
    private TargetState targetState;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private String name;
    private String description;
    private double version;

    @ElementCollection
    private Collection<String> categories;

    /*
    Mantenere una lista di Ticket aperta per prodotto perde di significato,
    infatti è un'informazione che non può essere inviata in formato JSON
    (causerebbe un ciclo infinito : il prodotto contiene ticket che a sua volta contiene
    un prodotto) , per ottenere una lista di Ticket aperti per prodotto sarà sufficente
    effettuare una query invocata da metodo REST.

    @JsonIgnore
    @OneToMany(mappedBy = "target")
    private List<Ticket> ticketList;
    */

    public Target() { }

    public Target(Long id, TargetState targetState){
        this.id = id;
        this.targetState = targetState;
    }

    public Target(String name, String description, double version) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.targetState = TargetState.ACTIVE;
    }

    public Target(String name, String description, double version, TargetState targetState){
        this.name = name;
        this.description = description;
        this.version = version;
        this.targetState = targetState;
    }

    /**
     * Costruttore del Target
     *
     * @param targetType Enumerazione che rappresenta il tipo di Target (Prodotto o Servizio).
     * @param targetState Enumerazione che rappresenta il tipo di Stato (RETIRED o ACTIVE).
     * @param name Il nome del Target.
     * @param description Breve descrizione del Target.
     * @param version Versione del Target alla quale si fa riferimento.
     */
    public Target(String name, String description, double version, TargetState targetState, TargetType targetType){
        this.name = name;
        this.description = description;
        this.version = version;
        this.targetState = targetState;
        this.targetType = targetType;
    }

    public Target(String name, String description, double version, TargetState targetState, TargetType targetType, List<String> categories){
        this.name = name;
        this.description = description;
        this.version = version;
        this.targetState = targetState;
        this.categories = categories;
        this.targetType = targetType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TargetState getTargetState() {
        return targetState;
    }

    public void setTargetState(TargetState targetState) {
        this.targetState = targetState;
    }

    /*
    @JsonIgnore
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @JsonProperty
    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        this.ticketList.remove(ticket);
    }
    */

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.TargetRestService
     * @param updatedData Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    public void updateTarget(@NotNull Target updatedData) {

        this.name = updatedData.name;
        this.description = updatedData.description;
        this.version = updatedData.version;
        this.targetState = updatedData.targetState;
        this.targetType = updatedData.targetType;
        this.categories = updatedData.categories;
    }


}
