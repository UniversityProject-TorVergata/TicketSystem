package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TeamManagerDao;
import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.entity.TeamManager;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TeamManagerController {

    @Autowired
    private TeamManagerDao teamManagerDao;

    @Transactional
    public @NotNull TeamManager insertTeamManager(@NotNull TeamManager teamManager) {
        TeamManager createdCompany = teamManagerDao.save(teamManager);
        return createdCompany;
    }

    @Transactional
    public @NotNull TeamManager updateTeamManager(@NotNull Long id, @NotNull TeamManager updatedData) throws NotFoundEntityException {

        TeamManager toBeUpdatedTeamManager = teamManagerDao.getOne(id);

        if (toBeUpdatedTeamManager == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeamManager.updateTeamManager(updatedData);
        TeamManager teamManager = teamManagerDao.save(toBeUpdatedTeamManager);

        return teamManager;
    }

    public TeamManager findTeamManagerById(@NotNull Long id) {
        TeamManager foundTeamManager = teamManagerDao.getOne(id);
        return foundTeamManager;
    }

    public boolean deleteTeamManager(@NotNull Long id) {
        if (!teamManagerDao.existsById(id)) {
            return false;
        }
        teamManagerDao.deleteById(id);
        return true;
    }

    public List<TeamManager> getTeamManagers() {

        return teamManagerDao.findAll();
    }
}
