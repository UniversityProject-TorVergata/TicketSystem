package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class TeamManager extends Assistant {

    private Long id;

    @OneToOne
    private Team managedTeam = this.team;

    public TeamManager() { }

    public TeamManager(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                     @NotNull String username, @NotNull String password, @NotNull String address, @NotNull Company company, @NotNull Team team) {
        super(fiscal_code, name, surname, email, username, password, address, company, team);
    }

    public void updateTeamManager(@NotNull TeamManager updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.team = updatedData.team; // TeamManager riassegnato ad un altro Team.
        this.address = updatedData.address;
    }


}
