package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    //@Transient
    @OneToMany
    private Collection<Assistant> assistantList;
    //Aggiunto da AlessioDL
    @ManyToOne
    private TeamManager teamManager;

    //Aggiunto da AlessioDL
    public Team(String name, String description, Collection<Assistant> assistantList, TeamManager teamManager) {
        this.name = name;
        this.description = description;
        this.assistantList = assistantList;
        this.teamManager = teamManager;
    }

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateTeam(@NotNull Team updatedData) {

        this.description = updatedData.description;
        this.name = updatedData.name;
        //Aggiunto da AlessioDL
        this.assistantList = updatedData.assistantList;
        this.teamManager = updatedData.teamManager;
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
