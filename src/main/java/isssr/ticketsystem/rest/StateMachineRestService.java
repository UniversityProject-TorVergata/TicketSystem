package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.StateMachineController;
import isssr.ticketsystem.entity.StateMachine;
import isssr.ticketsystem.enumeration.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path="state_machine")
public class StateMachineRestService {

    @Autowired
    private StateMachineController stateMachineController;

    /**
     * Metodo che restituisce tutti i nome della varie FSM.
     *
     * @return Collection con i nomi delle FSM
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<Collection<StateMachine>> getStateMachines(){
        Collection<StateMachine> stateMachines = stateMachineController.getStateMachines();
        if(stateMachines!=null)
            return new ResponseEntity<>(stateMachines,HttpStatus.OK);
        return new ResponseEntity<>(stateMachines,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo per inserire una nuova FSM nel DB. Nel corpo della FSM bisogna inserire:
     * - il nome della stessa
     * - una stringa in base 64 che rappresenta il file XML della FSM
     *
     * @return FSM creata
     */
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<StateMachine> insertStateMachine(@RequestBody StateMachine stateMachine){
        StateMachine savedStateMachine = stateMachineController.saveStateMachine(stateMachine);
        if(savedStateMachine != null)
            return new ResponseEntity<>(savedStateMachine,HttpStatus.OK);
        return new ResponseEntity<>(savedStateMachine,HttpStatus.NOT_ACCEPTABLE);
    }
}
