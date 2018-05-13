package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class TeamManager extends Assistant {

    private Long id;

    @Transient
    private Team managedTeam;

    public TeamManager() { }

    public TeamManager(Long id, Team team) {
        this.id = id;
        this.managedTeam = team;
    }

    public Team getTeam() {
        return this.managedTeam;
    }

    public void setTeam(Team team) {
        this.managedTeam = team;
    }

    public void updateTeamManager(@NotNull TeamManager updatedData) {

        this.managedTeam = updatedData.managedTeam; // TeamManager riassegnato ad un altro Team.
    }
}
