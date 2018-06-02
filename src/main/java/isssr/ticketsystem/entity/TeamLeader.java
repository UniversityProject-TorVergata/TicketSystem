package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Il Team Leader e' un TeamMember posto alla guida del Team a cui appartiene
 * ha le seguenti responsabilita' :
 * <ul>
 *     <li>Eseguire il pull sulla coda di Ticket assegnati dal Team Coordinator</li>
 *     <li>Assegnare ticket tra i Team Member del suo Team</li>
 * </ul>
 *
 */
@Entity
@Table(name = "team_leader")
@Getter
@Setter
public class TeamLeader extends TeamMember {

    private Long id;


    public TeamLeader() { }

    /**
     * Costruttore
     *
     * @param fiscal_code
     * @param name
     * @param surname
     * @param email
     * @param username
     * @param password
     * @param address
     * @param team

     */
    public TeamLeader(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                      @NotNull String username, @NotNull String password, @NotNull String address,
                      @NotNull Team team, @NotNull Team managedTeam) {
        super(fiscal_code, name, surname, email, username, password, address, team);

    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param registeredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser registeredUser) {

        TeamLeader updatedData = (TeamLeader) registeredUser;
        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.team = updatedData.team; // TeamLA riassegnato ad un altro Team.
        this.address = updatedData.address;
    }


}
