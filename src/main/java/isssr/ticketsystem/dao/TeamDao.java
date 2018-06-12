package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.entity.TeamCoordinator;
import isssr.ticketsystem.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TeamDao extends JpaRepository<Team, Long> {


    /**
     * Metodo Query che seleziona tutti i TeamMember di un dato Team
     *
     * @param id  ID del team di cui occorre trovare i TeamMember
     * @return lista di TeamMember del Team.
     */
    @Query("select u.teamMemberList from Team u where u.id = :id")
    Collection<TeamMember> getTeamMemberByTeamId(@Param("id") Long id);


    /**
     * Metodo Query che seleziona tutti i TeamMember di un dato Team dato il TeamLeader
     *
     * @param id del TeamLeader di cui interessano gli "Assistenti"
     * @return Lista dei TeamMember del Team.
     */
    @Query("select u.teamMemberList from Team u where u.teamLeader.id = :id")
    Collection<TeamMember> getTeamMemberByTeamLeaderId(@Param("id") Long id);




}
