package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TeamDao;
import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TeamController {

    @Autowired
    private TeamDao teamDao;

    @Transactional
    public @NotNull Team insertCompany(@NotNull Team companyUser) {
        Team createdCompany = teamDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull Team updateCompany(@NotNull Long id, @NotNull Team updatedData) throws NotFoundEntityException {

        Team toBeUpdatedTeam = teamDao.getOne(id);

        if (toBeUpdatedTeam == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeam.updateTeam(updatedData);
        Team updatedCompany = teamDao.save(toBeUpdatedTeam);

        return updatedCompany;
    }

    public Team findCompanyById(@NotNull Long id) {
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

    public List<Team> getCompanies() {

        return teamDao.findAll();
    }
}
