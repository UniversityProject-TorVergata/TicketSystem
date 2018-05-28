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

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("product")
 * attraverso i metodi definiti nella classe TargetController.
 */

@RestController
@RequestMapping(path = "product")
public class TargetRestService {

    @Autowired
    private TargetController targetController;

    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il target viene inserito nel DB.
     * @param product target che va aggiunto al DB.
     * @return target aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Target> insertProduct(@RequestBody Target product) {
        Target createdProduct = targetController.insertProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo lo stato del target specificato viene cambiato in
     * in RETIRED.
     * @param id Id del target il cui stato va aggiornato.
     * @return esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
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

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo lo stato del target specificato viene cambiato in
     * in ACTIVE.
     * @param id Id del target il cui stato va aggiornato.
     * @return esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
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

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il target specificato presente nel DB viene aggiornato.
     * @param id Id del target da aggiornare.
     * @param product target con le info aggiornate da mettere nel DB.
     * @return target eventualmente aggiornato + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
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

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restituite le info relative al target specificato.
     * @param id Id del target da visualizzare.
     * @return target da visualizzare + esito della richiesta HTTP
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Target> findProduct(@PathVariable Long id) {
        Target productFound = targetController.findProductById(id);
        return new ResponseEntity<>(productFound, productFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    /**
     * Metodo usato per la gestione di una Delete che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il target specificato viene cancellato dal DB.
     * @param id Id del target che va cancellato dal DB.
     * @return target cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deletedProduct = targetController.deleteTarget(id);
        return new ResponseEntity<>(deletedProduct, deletedProduct ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restuiti tutti i target presenti nel DB.
     * @return target presenti nel DB + esito della richiesta HTTP
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Target>> getProduct() {
        List<Target> product = targetController.getTarget();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
