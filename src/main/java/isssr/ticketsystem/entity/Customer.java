package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Il Customer e' l'utente che si rivolge al sistema di Ticketing per ottenere assistenza su un Target da lui posseduto.
 *
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends RegisteredUser {

    public Customer() {}


    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedDataRegisteredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser updatedDataRegisteredUser) {
        Customer updatedData = (Customer) updatedDataRegisteredUser;
        super.update(updatedData);
    }


}



