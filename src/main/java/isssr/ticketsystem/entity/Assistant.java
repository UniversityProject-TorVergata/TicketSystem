package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "assistant")
@Getter
@Setter
public class Assistant extends CompanyUser {

    @Transient
    private List<Team> belongedTeam;

    public Assistant() {
    }

    public Assistant(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull Company company) {
        super(fiscal_code, name, surname, email, username, password, company);
    }

    public void addTeam(Team team) {
        this.belongedTeam.add(team);
    }

    public void removeTeam(Team team) {
        if (this.belongedTeam.contains(team))
            this.belongedTeam.remove(team);
    }

    public List<Team> getBelongedTeam() {
        return belongedTeam;
    }

    public void setBelongedTeam(List<Team> belongedTeam) {
        this.belongedTeam = belongedTeam;
    }

    public void updateAssistant(@NotNull Assistant updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
    }
}
