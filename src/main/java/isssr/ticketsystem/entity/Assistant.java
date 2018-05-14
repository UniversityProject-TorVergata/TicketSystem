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
    protected Team managedteam;

    public Assistant() { }

    public Assistant(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                     @NotNull String username, @NotNull String password, @NotNull String address, @NotNull Company company, @NotNull Team team) {
        super(fiscal_code, name, surname, email, username, password, company, address);
        this.managedteam = team;
    }

    public void setTeam(Team team) {

        this.managedteam = team;
    }

    public Team getTeam() {

        return this.managedteam;
    }

    public void updateAssistant(@NotNull Assistant updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.managedteam = updatedData.managedteam; // Assistente riassegnato ad un altro Team.
        this.address = updatedData.address;
    }
}
