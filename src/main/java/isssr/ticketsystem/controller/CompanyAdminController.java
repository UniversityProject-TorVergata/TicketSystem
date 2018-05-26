package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.CompanyAdminDao;
import isssr.ticketsystem.entity.Admin;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CompanyAdminController {

    /*
    @Autowired
    private CompanyAdminDao companyAdminDao;

    @Transactional
    public @NotNull Admin insertCompanyAdmin(@NotNull Admin admin) {
        Admin createdAdmin = companyAdminDao.save(admin);
        return createdAdmin;
    }

    @Transactional
    public @NotNull Admin updateCompanyAdmin(@NotNull Long id, @NotNull Admin updatedData) throws NotFoundEntityException {

        Admin toBeUpdatedAdmin = companyAdminDao.getOne(id);

        if (toBeUpdatedAdmin == null)
            throw new NotFoundEntityException();

        toBeUpdatedAdmin.update(updatedData);
        Admin updatedAdmin = companyAdminDao.save(toBeUpdatedAdmin);

        return updatedAdmin;
    }

    public Admin findCompanyAdmminById(@NotNull Long id) {
        Admin foundAdmin = companyAdminDao.getOne(id);
        return foundAdmin;
    }

    public boolean deleteCompanyAdmin(@NotNull Long id) {
        if (!companyAdminDao.existsById(id)) {
            return false;
        }
        companyAdminDao.deleteById(id);
        return true;
    }

    public List<Admin> getCompanyAdmins() {

        return companyAdminDao.findAll();
    }*/
}
