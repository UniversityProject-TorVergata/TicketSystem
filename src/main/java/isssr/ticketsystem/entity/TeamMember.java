package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Componente di un Team si occupa della risoluzione di Ticket.
 *
 */
@Entity
@Table(name = "team_member")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TeamLeader.class, name = "TeamLeader"),
        }
)
public class TeamMember extends InternalUser {

    @OneToOne
    protected Team team;


    public TeamMember() { }


    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param registeredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser registeredUser) {
        TeamMember updatedData = (TeamMember) registeredUser;
        super.update(registeredUser);
        this.team = updatedData.team; // Assistente riassegnato ad un altro Team.

    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
