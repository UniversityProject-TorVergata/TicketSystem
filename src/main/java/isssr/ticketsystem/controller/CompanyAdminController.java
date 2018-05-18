package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.CompanyAdminDao;
import isssr.ticketsystem.entity.CompanyAdmin;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CompanyAdminController {

    @Autowired
    private CompanyAdminDao companyAdminDao;

    @Transactional
    public @NotNull CompanyAdmin insertCompanyAdmin(@NotNull CompanyAdmin companyAdmin) {
        CompanyAdmin createdCompanyAdmin = companyAdminDao.save(companyAdmin);
        return createdCompanyAdmin;
    }

    @Transactional
    public @NotNull CompanyAdmin updateCompanyAdmin(@NotNull Long id, @NotNull CompanyAdmin updatedData) throws NotFoundEntityException {

        CompanyAdmin toBeUpdatedCompanyAdmin = companyAdminDao.getOne(id);

        if (toBeUpdatedCompanyAdmin == null)
            throw new NotFoundEntityException();

        toBeUpdatedCompanyAdmin.updateCompanyAdmin(updatedData);
        CompanyAdmin updatedCompanyAdmin = companyAdminDao.save(toBeUpdatedCompanyAdmin);

        return updatedCompanyAdmin;
    }

    public CompanyAdmin findCompanyAdmminById(@NotNull Long id) {
        CompanyAdmin foundCompanyAdmin = companyAdminDao.getOne(id);
        return foundCompanyAdmin;
    }

    public boolean deleteCompanyAdmin(@NotNull Long id) {
        if (!companyAdminDao.existsById(id)) {
            return false;
        }
        companyAdminDao.deleteById(id);
        return true;
    }

    public List<CompanyAdmin> getCompanyAdmins() {

        return companyAdminDao.findAll();
    }
}
