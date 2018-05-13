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
    private List<Team> managedTeamList;

    public TeamManager(Long id, List<Team> teamList) {
        this.id = id;
        this.managedTeamList = teamList;
    }

    public List<Team> getTeamList() {
        return managedTeamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.managedTeamList = teamList;
    }

    public TeamManager() {
    }

    public void updateTeamManager(@NotNull TeamManager updatedData) {
        // TODO che ci va qui? Ohibò
    }
}
