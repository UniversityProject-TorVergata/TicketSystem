package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Superclasse che rappresenta il personale interno al sistema che si occupa,
 * di gestione della piattaforma e dei Ticket.
 *
 */
@Entity
@Table(name = "internal_user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "Admin"),
        @JsonSubTypes.Type(value = TeamMember.class, name = "TeamMember") ,
        @JsonSubTypes.Type(value = TeamCoordinator.class, name = "TeamCoordinator") }
)
public abstract class InternalUser extends RegisteredUser {

    /*
       Se company non fa più parte dell architettura del sistema ,che bisogno c'è di questa superclasse?

     */



    public InternalUser() {

    }


    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedDataRegisteredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser updatedDataRegisteredUser) {

        InternalUser updatedData = (InternalUser) updatedDataRegisteredUser;
        super.update(updatedData);
    }
}
