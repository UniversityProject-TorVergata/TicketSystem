package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ProductManagerDao;
import isssr.ticketsystem.entity.ProductManager;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class ProductManagerController {

    @Autowired
    private ProductManagerDao productManagerDao;

    @Transactional
    public @NotNull ProductManager insertCompany(@NotNull ProductManager companyUser) {
        ProductManager createdCompany = productManagerDao.save(companyUser);
        return createdCompany;
    }

    @Transactional
    public @NotNull ProductManager updateCompany(@NotNull Long id, @NotNull ProductManager updatedData) throws NotFoundEntityException {

        ProductManager toBeUpdatedProductManager = productManagerDao.getOne(id);

        if (toBeUpdatedProductManager == null)
            throw new NotFoundEntityException();

        toBeUpdatedProductManager.updateProductManager(updatedData);
        ProductManager updatedCompany = productManagerDao.save(toBeUpdatedProductManager);

        return updatedCompany;
    }

    public ProductManager findCompanyById(@NotNull Long id) {
        ProductManager foundProductManager = productManagerDao.getOne(id);
        return foundProductManager;
    }

    public boolean deleteProductManager(@NotNull Long id) {
        if (!productManagerDao.existsById(id)) {
            return false;
        }
        productManagerDao.deleteById(id);
        return true;
    }

    public List<ProductManager> getCompanies() {

        return productManagerDao.findAll();
    }
}
