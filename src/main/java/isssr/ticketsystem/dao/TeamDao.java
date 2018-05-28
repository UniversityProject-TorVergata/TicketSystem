package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TeamDao extends JpaRepository<Team, Long> {

    @Query("select u.teamMemberList from Team u where u.id = :id")
    Collection<TeamMember> getTeamMemberByTeamId(@Param("id") Long id);

    @Query("select u.teamMemberList from Team u where u.teamLeader.id = :id")
    Collection<TeamMember> getTeamMemberByTeamLeaderId(@Param("id") Long id);

}
