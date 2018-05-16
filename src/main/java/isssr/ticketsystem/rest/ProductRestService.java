package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.CompanyController;
import isssr.ticketsystem.controller.ProductController;
import isssr.ticketsystem.entity.Company;
import isssr.ticketsystem.entity.Product;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "product")
public class ProductRestService {

    @Autowired
    private ProductController productController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        Product createdProduct = productController.insertProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct;
        try {
            updatedProduct =  productController.updateProduct(id, product);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        Product productFound = productController.findProductById(id);
        return new ResponseEntity<>(productFound, productFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deletedProduct = productController.deleteProduct(id);
        return new ResponseEntity<>(deletedProduct, deletedProduct ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProduct() {
        List<Product> product = productController.getProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
