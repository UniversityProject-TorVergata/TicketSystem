package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProblemArea problemArea;
    //private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private Collection<Assistant> assistantList;

    @JsonIgnore
    @OneToOne(mappedBy = "managedTeam")
    private TeamManager teamManager;

    public Team(String name, ProblemArea problemArea, Collection<Assistant> assistantList, TeamManager teamManager) {
        this.name = name;
        this.problemArea =problemArea;
        this.assistantList = assistantList;
        this.teamManager = teamManager;
    }

    public Team(String name) {
        this.name = name;

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

    @JsonIgnore
    public Collection<Assistant> getAssistantList() {
        return assistantList;
    }

    @JsonProperty
    public void setAssistantList(Collection<Assistant> assistantList) {
        this.assistantList = assistantList;
    }

    @JsonIgnore
    public TeamManager getTeamManager() {
        return teamManager;
    }

    @JsonProperty
    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public void addAssistant(Assistant assistant) {
        this.assistantList.add(assistant);
    }

    public void removeAssistent(Assistant assistant) {

        this.assistantList.remove(assistant);
    }

    public void updateTeam(@NotNull Team updatedData) {

        this.problemArea = updatedData.problemArea;
        this.name = updatedData.name;
        this.assistantList = updatedData.assistantList;
        this.teamManager = updatedData.teamManager;
    }


}
