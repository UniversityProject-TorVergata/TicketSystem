package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.TargetController;
import isssr.ticketsystem.entity.Target;
import isssr.ticketsystem.enumeration.TargetState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("product")
 * attraverso i metodi definiti nella classe TargetController.
 */

@RestController
@RequestMapping(path = "target")
@SuppressWarnings("ConstantConditions")
public class TargetRestService {

    private final TargetController targetController;

    @Autowired
    public TargetRestService(TargetController targetController) {
        this.targetController = targetController;
    }

    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il target viene inserito nel DB.
     * @param target target che va aggiunto al DB.
     * @return target aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Target> insertTarget(@RequestBody Target target) {
        Target createdTarget = targetController.insertTarget(target);
        return new ResponseEntity<>(createdTarget, HttpStatus.CREATED);
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
    public ResponseEntity<Target> retireTarget(@PathVariable Long id) {
        Target updatedTarget = targetController.changeStateTarget(id,TargetState.RETIRED);
        if(updatedTarget != null)
            return new ResponseEntity<>(updatedTarget, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTarget, HttpStatus.NOT_FOUND);

    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo lo stato del target specificato viene cambiato in
     * in ACTIVE.
     * @param id Id del target il cui stato va aggiornato.
     * @return esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path="/rehab/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Target> rehabTarget(@PathVariable Long id) {
        Target updatedTarget = targetController.changeStateTarget(id, TargetState.ACTIVE);
        if(updatedTarget != null)
            return new ResponseEntity<>(updatedTarget, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTarget, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il target specificato presente nel DB viene aggiornato.
     * @param id Id del target da aggiornare.
     * @param target target con le info aggiornate da mettere nel DB.
     * @return target eventualmente aggiornato + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Target> updateTarget(@PathVariable Long id, @RequestBody Target target) {
        Target updatedTarget = targetController.updateTarget(id, target);
        if(updatedTarget != null)
            return new ResponseEntity<>(updatedTarget, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTarget, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restuiti tutti i target presenti nel DB.
     * @return target presenti nel DB + esito della richiesta HTTP
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Target>> getTarget() {
        List<Target> targetList = targetController.getTarget();
        if(targetList !=null)
            return new ResponseEntity<>(targetList, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restuiti tutti i target attivi presenti nel DB.
     * @return target presenti nel DB + esito della richiesta HTTP
     * @see isssr.ticketsystem.controller.TargetController
     */
    @RequestMapping(path = "/active", method = RequestMethod.GET)
    public ResponseEntity<List<Target>> getActiveTarget() {
        List<Target> targetList = targetController.getActiveTarget();
        if(targetList !=null)
            return new ResponseEntity<>(targetList, HttpStatus.OK);
        else
            return new ResponseEntity<>(targetList,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "getActualStates/{targetID}/{systemRole}")
    public ResponseEntity<List<String>> getActualStates(@PathVariable("targetID") Long targetID, @PathVariable("systemRole")
                                                     String role){
        List<String> states = targetController.getActualStates(targetID,role);
        if(states.size()!=0)
            return  new ResponseEntity<>(states,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "getNextStates/{targetID}/{currentState}")
    public ResponseEntity<ArrayList<ArrayList<String>>> getNextStates(@PathVariable("targetID") Long targetID, @PathVariable("currentState")
            String currentState){
        ArrayList<ArrayList<String>> states = targetController.getNextStates(targetID,currentState);
        if(states.size()!=0)
            return  new ResponseEntity<>(states,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
