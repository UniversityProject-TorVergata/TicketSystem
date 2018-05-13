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

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TeamManagerController {

    @Autowired
    private TeamManagerDao teamManagerDao;

    @Transactional
    public @NotNull TeamManager insertCompany(@NotNull TeamManager companyUser) {
        TeamManager createdCompany = teamManagerDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull TeamManager updateCompany(@NotNull Long id, @NotNull TeamManager updatedData) throws NotFoundEntityException {

        TeamManager toBeUpdatedTeamManager = teamManagerDao.getOne(id);

        if (toBeUpdatedTeamManager == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeamManager.updateTeamManager(updatedData);
        TeamManager updatedCompany = teamManagerDao.save(toBeUpdatedTeamManager);

        return updatedCompany;
    }

    public TeamManager findCompanyById(@NotNull Long id) {
        TeamManager foundTeamManager = teamManagerDao.getOne(id);
        return foundTeamManager;
    }

    public boolean deleteTeam(@NotNull Long id) {
        if (!teamManagerDao.existsById(id)) {
            return false;
        }
        teamManagerDao.deleteById(id);
        return true;
    }

    public List<TeamManager> getCompanies() {

        return teamManagerDao.findAll();
    }
}
