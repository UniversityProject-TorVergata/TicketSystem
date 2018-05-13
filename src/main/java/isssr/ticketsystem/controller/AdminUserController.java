package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.AdminUserDao;
import isssr.ticketsystem.dao.AdminUserDao;
import isssr.ticketsystem.entity.AdminUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class AdminUserController {

    @Autowired
    private AdminUserDao adminUserDao;

    @Transactional
    public @NotNull AdminUser insertCompany(@NotNull AdminUser companyUser) {
        AdminUser createdCompany = adminUserDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull AdminUser updateCompany(@NotNull Long id, @NotNull AdminUser updatedData) throws NotFoundEntityException {

        AdminUser toBeUpdatedAdminUser = adminUserDao.getOne(id);

        if (toBeUpdatedAdminUser == null)
            throw new NotFoundEntityException();

        toBeUpdatedAdminUser.updateAdminUser(updatedData);
        AdminUser updatedCompany = adminUserDao.save(toBeUpdatedAdminUser);

        return updatedCompany;
    }

    public AdminUser findCompanyById(@NotNull Long id) {
        AdminUser foundAdminUser = adminUserDao.getOne(id);
        return foundAdminUser;
    }

    public boolean deleteAdminUser(@NotNull Long id) {
        if (!adminUserDao.existsById(id)) {
            return false;
        }
        adminUserDao.deleteById(id);
        return true;
    }

    public List<AdminUser> getCompanies() {

        return adminUserDao.findAll();
    }
}
