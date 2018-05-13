package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "entity")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Transient
    private List<Assistant> assistantList;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateTeam(@NotNull Team updatedData) {

        this.description = updatedData.description;
        this.name = updatedData.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAssistant(Assistant assistant) {
        this.assistantList.add(assistant);
    }
}
