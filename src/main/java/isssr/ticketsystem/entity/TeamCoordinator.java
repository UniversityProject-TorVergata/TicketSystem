package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Il Team Coordinator che fa parte del personale interno ha le seguenti responsabilita'
 * <ul>
 *     <li>Creare Team</li>
 *     <li>Assegnare Ticket al Team Leader di un Team</li>
 *     <li>Assegnare i Team Member a un Team</li>
 *     <li>Modificare informazioni presenti su un Ticket e in particolare aggiungerne</li>
 *     <li>Cestinare un Ticket</li>
 *     <li>Indicare Priorita' e Tipologia effettiva di un Ticket</li>
 * </ul>
 *
 */
@Entity
@Table(name = "team_coordinator")
@Getter
@Setter
public class TeamCoordinator extends InternalUser {
    /*
    Il Team Coordinator gestisce tutti i prodotti nel sistema
    @OneToOne
    private Target managedTarget;
    */
    public TeamCoordinator() { }



    public TeamCoordinator(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                           @NotNull String username, @NotNull String password, Target managedTarget, @NotNull String address) {
        super(fiscal_code, name, surname, email, username, password, address);

    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param registeredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser registeredUser) {

        TeamCoordinator updatedData = (TeamCoordinator) registeredUser;
        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.address = updatedData.address;
    }

    @Override
    public String toString() {
        return "TeamCoordinator{}";
    }
}
