package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.CompanyAdminDao;
import isssr.ticketsystem.entity.CompanyAdmin;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class CompanyAdminController {

    @Autowired
    private CompanyAdminDao companyAdminDao;

    @Transactional
    public @NotNull CompanyAdmin insertCompany(@NotNull CompanyAdmin companyUser) {
        CompanyAdmin createdCompany = companyAdminDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull CompanyAdmin updateCompany(@NotNull Long id, @NotNull CompanyAdmin updatedData) throws NotFoundEntityException {

        CompanyAdmin toBeUpdatedCompanyAdmin = companyAdminDao.getOne(id);

        if (toBeUpdatedCompanyAdmin == null)
            throw new NotFoundEntityException();

        toBeUpdatedCompanyAdmin.updateCompanyAdmin(updatedData);
        CompanyAdmin updatedCompany = companyAdminDao.save(toBeUpdatedCompanyAdmin);

        return updatedCompany;
    }

    public CompanyAdmin findCompanyById(@NotNull Long id) {
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

    public List<CompanyAdmin> getCompanies() {

        return companyAdminDao.findAll();
    }
}
