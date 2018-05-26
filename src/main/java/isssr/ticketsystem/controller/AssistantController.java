package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.AssistantDao;
import isssr.ticketsystem.entity.TeamMember;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class AssistantController {
    /*
    @Autowired
    private AssistantDao assistantDao;

    @Transactional
    public @NotNull TeamMember insertAssistant(@NotNull TeamMember teamMember) {
        TeamMember createdTeamMember = assistantDao.save(teamMember);
        return createdTeamMember;
    }

    @Transactional
    public @NotNull TeamMember updateAssistant(@NotNull Long id, @NotNull TeamMember updatedData) throws NotFoundEntityException {

        TeamMember toBeUpdatedTeamMember = assistantDao.getOne(id);

        if (toBeUpdatedTeamMember == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeamMember.update(updatedData);
        TeamMember updatedTeamMember = assistantDao.save(toBeUpdatedTeamMember);

        return updatedTeamMember;
    }

    public TeamMember findCompanyById(@NotNull Long id) {
        TeamMember foundTeamMember = assistantDao.getOne(id);
        return foundTeamMember;
    }

    public boolean deleteAssistant(@NotNull Long id) {
        if (!assistantDao.existsById(id)) {
            return false;
        }
        assistantDao.deleteById(id);
        return true;
    }

    public List<TeamMember> getAssitants() {

        return assistantDao.findAll();
    }*/
}
