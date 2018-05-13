package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.AssistantDao;
import isssr.ticketsystem.entity.Assistant;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class AssistantController {

    @Autowired
    private AssistantDao assistantDao;

    @Transactional
    public @NotNull Assistant insertCompany(@NotNull Assistant companyUser) {
        Assistant createdCompany = assistantDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull Assistant updateCompany(@NotNull Long id, @NotNull Assistant updatedData) throws NotFoundEntityException {

        Assistant toBeUpdatedAssistant = assistantDao.getOne(id);

        if (toBeUpdatedAssistant == null)
            throw new NotFoundEntityException();

        toBeUpdatedAssistant.updateAssistant(updatedData);
        Assistant updatedCompany = assistantDao.save(toBeUpdatedAssistant);

        return updatedCompany;
    }

    public Assistant findCompanyById(@NotNull Long id) {
        Assistant foundAssistant = assistantDao.getOne(id);
        return foundAssistant;
    }

    public boolean deleteAssistant(@NotNull Long id) {
        if (!assistantDao.existsById(id)) {
            return false;
        }
        assistantDao.deleteById(id);
        return true;
    }

    public List<Assistant> getCompanies() {

        return assistantDao.findAll();
    }
}
