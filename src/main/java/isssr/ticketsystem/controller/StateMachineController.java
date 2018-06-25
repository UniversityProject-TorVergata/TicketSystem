package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.StateMachineDao;
import isssr.ticketsystem.entity.StateMachine;
import isssr.ticketsystem.enumeration.State;
import isssr.ticketsystem.state_machine.Action.FSMAction;
import isssr.ticketsystem.state_machine.FSM.FSM;
import isssr.ticketsystem.state_machine.States.FSMState;
import isssr.ticketsystem.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class StateMachineController {

    @Autowired
    private StateMachineDao stateMachineDao;

    public Collection<StateMachine> getStateMachines(){
        return stateMachineDao.findAll();
    }

    public StateMachine saveStateMachine(StateMachine stateMachine){

        String savedStateMachine = stateMachine.getBase64StateMachine();
        String relativePath = "./src/main/java/isssr/ticketsystem/state_machine/xml_files/" ;
        FileManager.convertStringToFile(savedStateMachine,stateMachine.getName(),relativePath);
        stateMachineValidation(relativePath +
            stateMachine.getName()+".xml");
        return stateMachineDao.save(stateMachine);

    }

    public boolean stateMachineValidation(String SMPath){
        FSM stateMachine = null;
        try {
            stateMachine = new FSM(SMPath, new FSMAction() {
                @Override
                public boolean action(String curState, String message, String nextState, Object args) {
                    System.out.println(curState + ":" + message + " : " + nextState);

                    // Here we can implement our login of how we wish to handle
                    // an action

                    return true;
                }
            });
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }


        List<FSMState> states = stateMachine.getAllStates();

        //Controllo sui nomi degli stati.
        for(FSMState state : states ){

            String stateName = state.getCurrentState();
            if(!State.validateState(stateName))
                return false;


        }

        stateMachine.getStateInformation(stateMachine.getCurrentState());

        //TODO COntrolli su ruolo presenza Validation o Dispatching , Closed;
        //TODO HARD controllo macchina connessa.

        return true;

    }



}
