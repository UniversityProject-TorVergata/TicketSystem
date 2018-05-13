package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ThirdPartyCustomerDao;
import isssr.ticketsystem.entity.ThirdPartyCustomer;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class ThirdPartyCustomerController {

    @Autowired
    private ThirdPartyCustomerDao thirdPartyCustomerDao;

    @Transactional
    public @NotNull ThirdPartyCustomer insertCompany(@NotNull ThirdPartyCustomer companyUser) {
        ThirdPartyCustomer createdCompany = thirdPartyCustomerDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull ThirdPartyCustomer updateCompany(@NotNull Long id, @NotNull ThirdPartyCustomer updatedData) throws NotFoundEntityException {

        ThirdPartyCustomer toBeUpdatedThirdPartyCustomer = thirdPartyCustomerDao.getOne(id);

        if (toBeUpdatedThirdPartyCustomer == null)
            throw new NotFoundEntityException();

        toBeUpdatedThirdPartyCustomer.updateThirdPartyCustomer(updatedData);
        ThirdPartyCustomer updatedCompany = thirdPartyCustomerDao.save(toBeUpdatedThirdPartyCustomer);

        return updatedCompany;
    }

    public ThirdPartyCustomer findCompanyById(@NotNull Long id) {
        ThirdPartyCustomer foundThirdPartyCustomer = thirdPartyCustomerDao.getOne(id);
        return foundThirdPartyCustomer;
    }

    public boolean deleteThirdPartyCustomer(@NotNull Long id) {
        if (!thirdPartyCustomerDao.existsById(id)) {
            return false;
        }
        thirdPartyCustomerDao.deleteById(id);
        return true;
    }

    public List<ThirdPartyCustomer> getCompanies() {

        return thirdPartyCustomerDao.findAll();
    }
}
