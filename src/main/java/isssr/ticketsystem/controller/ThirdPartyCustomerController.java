package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ThirdPartyCustomerDao;
import isssr.ticketsystem.entity.Customer;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ThirdPartyCustomerController {

    @Autowired
    private ThirdPartyCustomerDao thirdPartyCustomerDao;

    @Transactional
    public @NotNull Customer insertThirdParyCustomer(@NotNull Customer customer) {
        Customer createdCustomer = thirdPartyCustomerDao.save(customer);
        return createdCustomer;
    }

    @Transactional
    public @NotNull Customer updateThirdParyCustomer(@NotNull Long id, @NotNull Customer updatedData) throws NotFoundEntityException {

        Customer toBeUpdatedCustomer = thirdPartyCustomerDao.getOne(id);

        if (toBeUpdatedCustomer == null)
            throw new NotFoundEntityException();

        toBeUpdatedCustomer.update(updatedData);
        Customer customer = thirdPartyCustomerDao.save(toBeUpdatedCustomer);

        return customer;
    }

    public Customer findCompanyById(@NotNull Long id) {
        Customer foundCustomer = thirdPartyCustomerDao.getOne(id);
        return foundCustomer;
    }

    public boolean deleteThirdPartyCustomer(@NotNull Long id) {
        if (!thirdPartyCustomerDao.existsById(id)) {
            return false;
        }
        thirdPartyCustomerDao.deleteById(id);
        return true;
    }

    public List<Customer> getThirdPartyCustomers() {

        return thirdPartyCustomerDao.findAll();
    }
}
