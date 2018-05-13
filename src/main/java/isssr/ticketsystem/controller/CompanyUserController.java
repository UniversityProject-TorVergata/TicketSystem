package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.CompanyUserDao;
import isssr.ticketsystem.entity.CompanyUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class CompanyUserController {

    @Autowired
    private CompanyUserDao companyUserDao;

    @Transactional
    public @NotNull CompanyUser insertCompany(@NotNull CompanyUser companyUser) {
        CompanyUser createdCompany = companyUserDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull CompanyUser updateCompany(@NotNull Long id, @NotNull CompanyUser updatedData) throws NotFoundEntityException {

        CompanyUser toBeUpdatedCompanyUser = companyUserDao.getOne(id);

        if (toBeUpdatedCompanyUser == null)
            throw new NotFoundEntityException();

        toBeUpdatedCompanyUser.updateCompanyUser(updatedData);
        CompanyUser updatedCompany = companyUserDao.save(toBeUpdatedCompanyUser);

        return updatedCompany;
    }

    public CompanyUser findCompanyById(@NotNull Long id) {
        CompanyUser foundCompanyUser = companyUserDao.getOne(id);
        return foundCompanyUser;
    }

    public boolean deleteCompanyUser(@NotNull Long id) {
        if (!companyUserDao.existsById(id)) {
            return false;
        }
        companyUserDao.deleteById(id);
        return true;
    }

    public List<CompanyUser> getCompanies() {

        return companyUserDao.findAll();
    }
}
