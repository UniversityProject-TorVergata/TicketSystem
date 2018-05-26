package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "team_member")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TeamLeader.class, name = "TeamLeader"),
        }
)
/**
 * Componente di un Team si occupa della risoluzione di Ticket.
 *
 */
public class TeamMember extends InternalUser {

    @OneToOne
    protected Team team;


    public TeamMember() { }

    /**
     *
     *
     *
     * @param fiscal_code
     * @param name
     * @param surname
     * @param email
     * @param username
     * @param password
     * @param address
     * @param company
     * @param team Team a cui appartiene il Team Member
     */
    public TeamMember(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                      @NotNull String username, @NotNull String password, @NotNull String address, @NotNull Company company, @NotNull Team team) {
        super(fiscal_code, name, surname, email, username, password, company, address);
        this.team = team;
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param registeredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser registeredUser) {
        TeamMember updatedData = (TeamMember) registeredUser;

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.team = updatedData.team; // Assistente riassegnato ad un altro Team.
        this.address = updatedData.address;
    }


}
