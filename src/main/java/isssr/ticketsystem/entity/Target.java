package isssr.ticketsystem.entity;

import isssr.ticketsystem.enumeration.TargetState;
import isssr.ticketsystem.enumeration.TargetType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;


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

    /**
     *  NOME della Macchina a stati associata allo specifico Target.
     */
    private String stateMachineName;


    private String name;
    private String description;
    private double version;

    @ElementCollection
    private Collection<String> categories;


    public Target() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setTargetState(TargetState targetState) {
        this.targetState = targetState;
    }

    public String getStateMachineName() {
        return this.stateMachineName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
