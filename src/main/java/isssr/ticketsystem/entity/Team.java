package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import isssr.ticketsystem.enumeration.ProblemArea;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Formato da Team Coordinator,composto da Team Member e guidato da un Team Leader.
 * Ha una specifica area di risoluzione di problemi.
 *
 */
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
    private List<TeamMember> teamMemberList;

    @JsonIgnore
    @OneToOne
    private TeamLeader teamLeader;

    /**
     *
     *
     * @param name
     * @param problemArea Area di risoluzione a cui si dedica il team
     * @param teamMemberList lista dei TeamMember
     * @param teamLeader TeamLedaer del Team
     */
    public Team(String name, ProblemArea problemArea, List<TeamMember> teamMemberList, TeamLeader teamLeader) {
        this.name = name;
        this.problemArea =problemArea;
        this.teamMemberList = teamMemberList;
        this.teamLeader = teamLeader;
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
    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    @JsonProperty
    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    @JsonIgnore
    public TeamLeader getTeamLeader() {
        return teamLeader;
    }

    @JsonProperty
    public void setTeamLeader(TeamLeader teamLeader) {
        this.teamLeader = teamLeader;
    }

    public void addTeamMember(TeamMember teamMember) {
        this.teamMemberList.add(teamMember);
    }

    public void removeAssistent(TeamMember teamMember) {

        this.teamMemberList.remove(teamMember);
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.TeamRestService
     * @param updatedData Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    public void updateTeam(@NotNull Team updatedData) {

        this.problemArea = updatedData.problemArea;
        this.name = updatedData.name;
        this.teamMemberList = updatedData.teamMemberList;
        this.teamLeader = updatedData.teamLeader;
    }

    public void setProblemArea(ProblemArea problemArea) {
        this.problemArea = problemArea;
    }




}
