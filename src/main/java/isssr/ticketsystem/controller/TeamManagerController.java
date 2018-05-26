package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TeamManagerDao;
import isssr.ticketsystem.entity.TeamLeader;
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
    public @NotNull TeamLeader insertTeamManager(@NotNull TeamLeader teamLeader) {
        TeamLeader createdCompany = teamManagerDao.save(teamLeader);
        return createdCompany;
    }

    @Transactional
    public @NotNull TeamLeader updateTeamManager(@NotNull Long id, @NotNull TeamLeader updatedData) throws NotFoundEntityException {

        TeamLeader toBeUpdatedTeamLeader = teamManagerDao.getOne(id);

        if (toBeUpdatedTeamLeader == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeamLeader.update(updatedData);
        TeamLeader teamLeader = teamManagerDao.save(toBeUpdatedTeamLeader);

        return teamLeader;
    }

    public TeamLeader findTeamManagerById(@NotNull Long id) {
        TeamLeader foundTeamLeader = teamManagerDao.getOne(id);
        return foundTeamLeader;
    }

    public boolean deleteTeamManager(@NotNull Long id) {
        if (!teamManagerDao.existsById(id)) {
            return false;
        }
        teamManagerDao.deleteById(id);
        return true;
    }

    public List<TeamLeader> getTeamManagers() {

        return teamManagerDao.findAll();
    }
}
