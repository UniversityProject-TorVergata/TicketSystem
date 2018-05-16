package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ProductDao;
import isssr.ticketsystem.entity.Company;
import isssr.ticketsystem.entity.Product;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Transactional
    public @NotNull Product insertProduct(@NotNull Product product) {
        Product createdProduct = productDao.save(product);
        return createdProduct;
    }

    @Transactional
    public @NotNull Product updateProduct(@NotNull Long id, @NotNull Product updatedData) throws NotFoundEntityException {

        Product toBeUpdatedProduct = productDao.getOne(id);

        if (toBeUpdatedProduct == null)
            throw new NotFoundEntityException();

        toBeUpdatedProduct.updateProduct(updatedData);
        Product updatedProduct = productDao.save(toBeUpdatedProduct);

        return toBeUpdatedProduct;
    }

    public Product findProductById(@NotNull Long id) {
        Product foundProduct = productDao.getOne(id);
        return foundProduct;
    }

    public boolean deleteProduct(@NotNull Long id) {
        if (!productDao.existsById(id)) {
            return false;
        }
        productDao.deleteById(id);
        return true;
    }

    public List<Product> getProduct() {

        return productDao.findAll();
    }
}
