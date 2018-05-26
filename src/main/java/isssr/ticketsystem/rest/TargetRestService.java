package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.TargetController;
import isssr.ticketsystem.entity.Target;
import isssr.ticketsystem.entity.TargetState;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "product")
public class TargetRestService {

    @Autowired
    private TargetController targetController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Target> insertProduct(@RequestBody Target product) {
        Target createdProduct = targetController.insertProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @RequestMapping(path="/retire/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Target> retireProduct(@PathVariable Long id) {
        Target updatedTarget;
        try {
            updatedTarget =  targetController.changeStateProduct(id,TargetState.RETIRED);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(path="/reab/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Target> reabProduct(@PathVariable Long id) {
        Target updatedTarget;
        try {
            updatedTarget =  targetController.changeStateProduct(id,TargetState.ACTIVE);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Target> updateProduct(@PathVariable Long id, @RequestBody Target product) {
        Target updatedProduct;
        try {
            updatedProduct =  targetController.updateProduct(id, product);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Target> findProduct(@PathVariable Long id) {
        Target productFound = targetController.findProductById(id);
        return new ResponseEntity<>(productFound, productFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deletedProduct = targetController.deleteTarget(id);
        return new ResponseEntity<>(deletedProduct, deletedProduct ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Target>> getProduct() {
        List<Target> product = targetController.getTarget();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
