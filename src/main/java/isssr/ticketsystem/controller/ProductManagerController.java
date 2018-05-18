package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ProductManagerDao;
import isssr.ticketsystem.entity.ProductManager;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ProductManagerController {

    @Autowired
    private ProductManagerDao productManagerDao;

    @Transactional
    public @NotNull ProductManager insertProductManager(@NotNull ProductManager productManager) {
        ProductManager createdCompany = productManagerDao.save(productManager);
        return createdCompany;
    }

    @Transactional
    public @NotNull ProductManager updateProductManager(@NotNull Long id, @NotNull ProductManager updatedData) throws NotFoundEntityException {

        ProductManager toBeUpdatedProductManager = productManagerDao.getOne(id);

        if (toBeUpdatedProductManager == null)
            throw new NotFoundEntityException();

        toBeUpdatedProductManager.updateProductManager(updatedData);
        ProductManager updatedProductManager = productManagerDao.save(toBeUpdatedProductManager);

        return updatedProductManager;
    }

    public ProductManager findProductManagerById(@NotNull Long id) {
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

    public List<ProductManager> getProductManager() {

        return productManagerDao.findAll();
    }
}
