package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Rappresenta l'amministratore di sistema ha le seguenti responsabilita' :
 * <ul>
 *     <li>Assumere personale Interno (Team Coordinator,Team Member,Team Leader)</li>
 *     <li>Registrare Target nel sistema</li>
 * </ul>
 *
 */
@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin extends InternalUser {

    public Admin(){

    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedDataRegisteredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser updatedDataRegisteredUser) {

        Admin updatedData = (Admin) updatedDataRegisteredUser;
        super.update(updatedData);
    }




}
