package isssr.ticketsystem.controller;


import Action.FSMAction;
import FSM.*;
import States.FSMState;
import isssr.ticketsystem.dao.StateMachineDao;
import isssr.ticketsystem.entity.StateMachine;
import isssr.ticketsystem.enumeration.State;
import isssr.ticketsystem.enumeration.SystemRole;
import isssr.ticketsystem.util.FileManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

@Service
public class StateMachineController {

    @Autowired
    private StateMachineDao stateMachineDao;

    public Collection<StateMachine> getStateMachines(){
        return stateMachineDao.findAll();
    }

    public String saveStateMachine(StateMachine stateMachine){
        if(stateMachine.getBase64StateMachine()!=null) {
            String savedStateMachine = stateMachine.getBase64StateMachine();
            String relativePath = "./src/main/resources/state_machine/xml_files/";
            FileManager.convertStringToFile(savedStateMachine, stateMachine.getName(), relativePath);
            //Se la macchina a stati inserita non è valida restituisco errore.
            String result = stateMachineValidation(relativePath +
                    stateMachine.getName() + ".xml");
            if(result!=null)
                return result;
        }
        if(stateMachineDao.save(stateMachine)!=null)
            return null;
        return "ERROR SAVING STATE MACHINE";

    }

    private String stateMachineValidation(String SMPath){
        Log logger = LogFactory.getLog(getClass());
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
            logger.error("Invalid XML");
            return "INVALID XML";
        }

        //Controllo che il primo stato sia validation o dispatching.
        String startState = stateMachine.getCurrentState();
        if(!(startState.equals(State.VALIDATION.toString()) ||
                startState.equals(State.DISPATCHING.toString()))){
            logger.error("INVALID DEPARTURE STATE : "+startState);
            return "INVALID START STATE : " + startState;
        }


        List<FSMState> states = stateMachine.getAllStates();
        //Controllo che Ruoli e Stati siano quelli del sistema
        boolean execution = false;
        for(FSMState state : states){
            if(state.getCurrentState().equals(State.EXECUTION.toString())){
                execution = true;
            }

            if(!controlStates(state.getCurrentState()))
                return "NOT ADMITTED STATE : " + state.getCurrentState();
            ArrayList<ArrayList<String>> state_info = stateMachine.getStateInformation(state.getCurrentState());
            ArrayList<String> roles = state_info.get(1);
            if(!controlRoles(roles))
                return "NOT ADIMETTED ROLES : " + roles;

            //se la lista dei prossimi stati è nulla sto analizzando l'ultimo stato
            //Controllo che sia CLOSED altrimenti restistuisco false
            if(state_info.get(2).size()==0){
                if(!state.getCurrentState().equals(State.CLOSED.toString())) {
                    logger.error("Invalid FINAL STATE "+state.getCurrentState());
                    return "INVALID FINAL STATE : " + state.getCurrentState();
                }
            }

        }

        if(!execution)
            return "NOT EXECUTION STATE IN THE STATE MACHINE";

        //Infine controllo che la macchina a stati non sia spezzata
        if(!controlFSMConnection(stateMachine))
            return "NOT CONNECTED STATE MACHINE";

        return null;

    }

    private boolean controlStates(String stateName){
        Log logger = LogFactory.getLog(getClass());
        //Controllo sui nomi degli stati.
        if(!State.validateState(stateName)) {
            logger.error("Invalid STATE : "+stateName);
            return false;
        }
        return true;

    }

    private boolean controlRoles(ArrayList<String> roles){
        Log logger = LogFactory.getLog(getClass());
        for(String role : roles){
            if(!SystemRole.validateRole(role)) {
                logger.error("Invalid ROLE : "+role);
                return false;
            }
        }
        return true;
    }

    private boolean controlFSMConnection(FSM stateMachine){
        Log logger = LogFactory.getLog(getClass());
        List<FSMState> states = stateMachine.getAllStates();
        ArrayList<String> statesString = new ArrayList<>();
        for(FSMState state : states){
            statesString.add(state.getCurrentState());
        }
        //Mappa in cui la chiave è il nome di stato il valore è true se lo stato è raggiungibile
        // false altrimenti
        HashMap<String,Boolean> connectionMap = new HashMap<>();

        //Lo stato di partenza è raggiungibile per definizione
        String startState = stateMachine.getCurrentState();
        connectionMap.put(startState,true);

        for(FSMState state : states){
            //Se lo stato analzizato non è presente nella connectionMap vi è inserito con valore false
            if(!connectionMap.containsKey(state.getCurrentState()))
                connectionMap.put(state.getCurrentState(),false);
            ArrayList<ArrayList<String>> state_info = stateMachine.getStateInformation(state.getCurrentState());
            ArrayList<String> nextStates = state_info.get(2);
            //Analizzo gli successivi i quali sono inseriti nella connectionMap
            //Con valore true;
            for(String nextState : nextStates){
                //Se un next state non è tra gli stati del sistema
                if(!State.validateState(nextState)) {
                    logger.error("INVALID NEXT STATE : "+nextState);
                    return false;
                }
                //Se un next state è negli stati del sistema ma non è presente nell FSM corrente
                if(!statesString.contains(nextState)) {
                    logger.error("NOT PRESENT NEXT STATE : "+nextState);
                    return false;
                }
                if(connectionMap.containsKey(nextState))
                    connectionMap.remove(nextState);
                connectionMap.put(nextState,true);
            }

        }

        //Infine iterando sulla connectionMap verifico che ogni stato abbia valore true
        //Ovvero sia raggiungbile in caso contrario restituisco false
        for(Map.Entry<String,Boolean> entry : connectionMap.entrySet()){
            if(!entry.getValue()) {
                logger.error("State Machine not connected at : "+entry.getKey());
                return false;
            }
        }
        return  true;

    }


    public List<String> getActualStates(String stateMachineName, String role) {
        FSM stateMachine = null;
        String SMPath = "./src/main/resources/state_machine/xml_files/"+stateMachineName+".xml";
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
        }
        List<FSMState> fsmStateList = stateMachine.getAllStates();
        ArrayList<String> outPutList = new ArrayList<>();
        for(FSMState fsmState:fsmStateList){
            ArrayList<ArrayList<String>> state_info = stateMachine.getStateInformation(fsmState.getCurrentState());
            ArrayList<String> roleList = state_info.get(1);
            ArrayList<String> stateList = state_info.get(2);
            for(String roleStr : roleList){
                if(roleStr.equals(role)){
                    int index = roleList.indexOf(roleStr);
                    if(!outPutList.contains(stateList.get(index)))
                        outPutList.add(stateList.get(index));
                }
                if(role.equals(SystemRole.TeamLeader.toString()) && roleStr.equals(SystemRole.TeamMember.toString())){
                    int index = roleList.indexOf(roleStr);
                    if(!outPutList.contains(stateList.get(index)))
                        outPutList.add(stateList.get(index));
                }
            }
        }
        return  outPutList;
    }

    public ArrayList<ArrayList<String>> getNextStates(String stateMachineName, String currentState) {
        FSM stateMachine = null;
        String SMPath = "./src/main/resources/state_machine/xml_files/"+stateMachineName+".xml";
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
        }
        List<FSMState> fsmStateList = stateMachine.getAllStates();
        ArrayList<ArrayList<String>> outPutList = new ArrayList<>();
        for(FSMState fsmState:fsmStateList){
            if(fsmState.getCurrentState().equals(currentState)) {
                ArrayList<ArrayList<String>> state_info = stateMachine.getStateInformation(fsmState.getCurrentState());
                outPutList.addAll(state_info);
                break;
            }
        }
        return  outPutList;
    }
}
