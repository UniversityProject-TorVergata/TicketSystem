package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.CompanyDao;
import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.entity.Company;
import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class CompanyController {

    @Autowired
    private CompanyDao companyDao;
    private RegisteredUserDao registeredUserDao;

    @Transactional
    public @NotNull Company insertCompany(@NotNull Company company) {
        RegisteredUser registeredUser = registeredUserDao.save(new RegisteredUser(
                company.getFiscal_code(), company.getName(), company.getSurname(),
                company.getEmail(), company.getUsername(), company.getPassword()));
        Company createdCompany = companyDao.save(company);
        return createdCompany;
    }

    @Transactional
    public @NotNull Company updateCompany(@NotNull Long id, @NotNull Company updatedData) throws NotFoundEntityException {

        Company toBeUpdatedCompany = companyDao.getOne(id);

        if (toBeUpdatedCompany == null)
            throw new NotFoundEntityException();

        //toBeUpdatedCompany.updateCompany(, updatedData);
        Company updatedCompany = companyDao.save(toBeUpdatedCompany);

        return updatedCompany;
    }

    public Company findCompanyById(@NotNull Long id) {
        Company foundCompany = companyDao.getOne(id);
        return foundCompany;
    }

    public boolean deleteCompany(@NotNull Long id) {
        if (!companyDao.existsById(id)) {
            return false;
        }
        companyDao.deleteById(id);
        return true;
    }

    public List<Company> getCompanys() {

        return companyDao.findAll();
    }
}
