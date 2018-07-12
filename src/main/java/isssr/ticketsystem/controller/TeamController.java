package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.dao.TeamDao;
import isssr.ticketsystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TeamController {

    private final TeamDao teamDao;
    private final RegisteredUserDao registeredUserDao;

    @Autowired
    public TeamController(TeamDao teamDao, RegisteredUserDao registeredUserDao) {
        this.teamDao = teamDao;
        this.registeredUserDao = registeredUserDao;
    }

    @Transactional
    public @NotNull Team insertTeam(@NotNull Team team) {
        List<TeamMember> teamMembers = team.getTeamMemberList();
        if(teamMembers != null) {
            for (TeamMember tm : teamMembers) {
                Optional<RegisteredUser> registeredUserOptional = registeredUserDao.findById(tm.getId());
                if (!registeredUserOptional.isPresent())
                    continue;
                if (!registeredUserOptional.get().getClass().equals(TeamMember.class))
                    continue;
                TeamMember teamMember = (TeamMember) registeredUserOptional.get();
                teamMember.setTeam(team);
                registeredUserDao.save(teamMember);
            }
        }
        if (team.getTeamLeader() != null) {
            Optional<RegisteredUser> registeredUserOptional = registeredUserDao.findById(team.getTeamLeader().getId());
            if (registeredUserOptional.isPresent()) {
                if (registeredUserOptional.get().getClass().equals(TeamLeader.class)) {
                    TeamLeader teamLeader = (TeamLeader) registeredUserOptional.get();
                    teamLeader.setTeam(team);
                    registeredUserDao.save(teamLeader);
                }
            }
        }

        return teamDao.save(team);
    }

    @Transactional
    public @NotNull Team updateTeam(@NotNull Long id, @NotNull Team updatedData) {

        Team toBeUpdatedTeam = teamDao.getOne(id);
        toBeUpdatedTeam.updateTeam(updatedData);
        return teamDao.save(toBeUpdatedTeam);
    }
/*
    public Team findTeamById(@NotNull Long id) {
        return teamDao.getOne(id);
    }*/

    public boolean deleteTeam(@NotNull Long id) {
        Optional<Team> optionalTeam = teamDao.findById(id);
        if(!optionalTeam.isPresent())
            return false;
        Team team = optionalTeam.get();

        if (!teamDao.existsById(id))
            return false;
        for(TeamMember teamMember:team.getTeamMemberList()){
            teamMember.setTeam(null);
        }
        team.getTeamMemberList().clear();
        teamDao.deleteById(id);
        return true;
    }

    public List<Team> getTeams() {

        return teamDao.findAll();
    }

    public Collection<TeamMember> getTeamMemberByTeamId(@NotNull Long id){

        return teamDao.getTeamMemberByTeamId(id);

    }

    public Collection<TeamMember> getTeamMemberByTeamLeaderId(@NotNull Long id){

        return teamDao.getTeamMemberByTeamLeaderId(id);

    }

    public TeamMember addTeamMember(@NotNull Long teamID, Long teamMemberID){
        Team teamToUpdate = teamDao.getOne(teamID);
        Optional<RegisteredUser> registeredUserOptional = registeredUserDao.findById(teamMemberID);
        RegisteredUser registeredUser;
        if(registeredUserOptional.isPresent())
            registeredUser = registeredUserOptional.get();
        else
            return null;
        TeamMember teamMember;
        if(registeredUser.getClass().equals(TeamMember.class) || registeredUser.getClass().equals(TeamLeader.class))
            teamMember = (TeamMember) registeredUser;
        else
            return null;
        teamToUpdate.addTeamMember(teamMember);
        teamDao.save(teamToUpdate);
        teamMember.setTeam(teamToUpdate);
        return registeredUserDao.save(teamMember);

    }

    public TeamLeader getTeamLeaderByTeamId(Long id) {
        Team team = teamDao.getOne(id);
        return team.getTeamLeader();

    }


}
