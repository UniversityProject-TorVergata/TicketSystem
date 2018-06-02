package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.dao.TeamDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TeamController {

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private RegisteredUserDao registeredUserDao;

    @Transactional
    public @NotNull Team insertTeam(@NotNull Team team) {
        Team createdTeam = teamDao.save(team);
        return createdTeam;
    }

    @Transactional
    public @NotNull Team updateTeam(@NotNull Long id, @NotNull Team updatedData) throws NotFoundEntityException {

        Team toBeUpdatedTeam = teamDao.getOne(id);

        if (toBeUpdatedTeam == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeam.updateTeam(updatedData);
        Team updatedCompany = teamDao.save(toBeUpdatedTeam);

        return updatedCompany;
    }

    public Team findTeamById(@NotNull Long id) {
        Team foundTeam = teamDao.getOne(id);
        return foundTeam;
    }

    public boolean deleteTeam(@NotNull Long id) {
        if (!teamDao.existsById(id)) {
            return false;
        }
        teamDao.deleteById(id);
        return true;
    }

    public List<Team> getTeams() {

        return teamDao.findAll();
    }

    public Collection<TeamMember> getTeamMemberByTeamId(@NotNull Long id){

        Collection<TeamMember> foundListTeamMember = teamDao.getTeamMemberByTeamId(id);
        return foundListTeamMember;
    }

    public Collection<TeamMember> getTeamMemberByTeamLeaderId(@NotNull Long id){

        Collection<TeamMember> foundListTeamMember = teamDao.getTeamMemberByTeamLeaderId(id);
        return foundListTeamMember;
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

    public Team updateProblemArea(@NotNull Long team_id, @NotNull ProblemArea problemArea){
        Team teamToUpdate = teamDao.getOne(team_id);
        teamToUpdate.setProblemArea(problemArea);
        teamDao.save(teamToUpdate);
        return teamToUpdate;
    }



}
