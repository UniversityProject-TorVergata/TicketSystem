package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "assistant")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TeamManager.class, name = "TeamManager"),
        }
)
public class Assistant extends CompanyUser {

    @OneToOne
    protected Team team;


    public Assistant() { }

    public Assistant(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                     @NotNull String username, @NotNull String password, @NotNull String address, @NotNull Company company, @NotNull Team team) {
        super(fiscal_code, name, surname, email, username, password, company, address);
        this.team = team;
    }


    public void updateAssistant(@NotNull Assistant updatedData) {

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
