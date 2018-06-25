package isssr.ticketsystem.state_machine.FSM;

import isssr.ticketsystem.state_machine.Action.FSMAction;
import isssr.ticketsystem.state_machine.Common.CustomXMLReader;
import isssr.ticketsystem.state_machine.States.FSMState;
import isssr.ticketsystem.state_machine.States.FSMStateAction;
import isssr.ticketsystem.state_machine.States.FSMStates;
import isssr.ticketsystem.state_machine.States.FSMTransitionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Class to allow creation of the FSM<br/>
 * 
 * This class allows developer to either specify the XML Configuration File or
 * can pass the input-stream of the XML Configuration File.<br/>
 * 
 * @author ANKIT
 */
public class FSM implements java.io.Serializable {
    
    /* Added to support Serializability */
    private static final long serialVersionUID = -4817986591227138567L;
    
    /*
     * Any FSM requires three things:
     * * States
     * * Roles
     * * Actions
     */
    private FSMStates _fsm;
    private transient FSMAction _role;
    private transient Object _sharedData;

    /**
     * Constructor allows to create a FSM from a specified file-name<br/>
     * and specified Actions<br/>
     * 
     * @param configFName : Configuration file-name.
     * @param role
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSM(String configFName, FSMAction role)
            throws ParserConfigurationException, SAXException, IOException {
        this._fsm = new FSMStates(configFName, !"".equals(configFName));
        this._role = role;
    }
    
    /**
     * Constructor allows to create an FSM from a specified file-name<br/>
     * and specified Actions along with Shared Data<br/>
     * 
     * @param configFName : Configuration file-name.
     * @param role
     * @param sharedData  : Specify any shared data required.
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSM(String configFName, FSMAction role, Object sharedData)
            throws ParserConfigurationException, SAXException, IOException {
            this(configFName, role);
            this._sharedData = sharedData;
    }
    
    /**
     * Constructor takes the default configuration file<br/>
     * Shall not be used in production environment<br/>
     * 
     * @param role
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Deprecated
    public FSM(FSMAction role)
            throws ParserConfigurationException, SAXException, IOException {
        this("", role);
    }
    
    /**
     * Constructor takes the default configuration file<br/>
     * Shall not be used in production environment<br/>
     * 
     * @param role
     * @param sharedData
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Deprecated
    public FSM(FSMAction role, Object sharedData)
            throws ParserConfigurationException, SAXException, IOException {
        this(role);
        this._sharedData = sharedData;
    }
    
    /**
     * Constructor takes the default configuration file<br/>
     * Shall not be used in production environment<br/>
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Deprecated
    public FSM() 
            throws ParserConfigurationException, SAXException, IOException {
        this("",null);
    }

    /**
     * Constructor allows to create a FSM from a specified Input-Stream<br/>
     * and specified Actions along with Shared data<br/>
     * 
     * @param configFStream Input Stream of the XML Configuration file.
     * @param role
     * @param sharedData Shared Data passed across in FSM
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSM(InputStream configFStream, FSMAction role, Object sharedData)
            throws ParserConfigurationException, SAXException, IOException {
        this._fsm = new FSMStates(configFStream);
        this._role = role;
        this._sharedData = sharedData;
    }

    /**
     * Constructor allows to create a FSM from a specified Input-Stream<br/>
     * and specified Actions<br/>
     * 
     * @param configFStream Input Stream of the XML Configuration file.
     * @param role
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSM(InputStream configFStream, FSMAction role)
            throws ParserConfigurationException, SAXException, IOException {
        this(configFStream, role, null);
    }
    /**
     * Method on receiving the Message Id, takes appropriate action<br/>
     * and on successful execution of the action Transitions to the new-state<br/>
     * as per the transition map.<br/>
     * 
     * @param recvdMsgId Received Message Id
     * 
     * @return Returns the Current State as String
     */
    public Object ProcessFSM(String recvdMsgId) {
        Object _r;
        _r = this._fsm.getCurrentState().getNewTransitionMap().get(recvdMsgId);
        if ( null != _r) {
            String[] _t = new String[2];
            _t[0] = ((FSMTransitionInfo)_r).getActionName();
            _t[1] = ((FSMTransitionInfo)_r).getNextState();
            boolean status = true;
            for (Object _f: this._fsm.getAllStates()) {
                if( ((FSMState)_f).getCurrentState().equals((String)_t[1])) {
                    /* Check if the action specific to each message exists
                       If not, then in this case call the generic action function
                    */
                    FSMStateAction _a = ((FSMState)_f).getBeforeTransition();
                    if (_a!=null) {
                        _a.stateTransition(((FSMState)_f).getCurrentState(), 
                                this._sharedData);
                    }
                    
                    FSMAction act = ((FSMTransitionInfo)_r).getAction();
                    if (act!=null) {
                        /* If customized action is declared, call an entry function */
                        act.entry(this._fsm.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                        status = act.action(this._fsm.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                    } else if ( null != this._role) {
                        status = 
                        this._role.action(this._fsm.getCurrentState().getCurrentState(),
                                (String)_t[0], (String)_t[1], this._sharedData);
                    }
                    
                    if(status) {
                        this._fsm.setCurrentState((FSMState)_f);
                        
                        if (act!=null) {
                            act.afterTransition(this._fsm.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                        }else if ( null != this._role) {
                            this._role.afterTransition(this._fsm.getCurrentState().getCurrentState(),
                                    (String)_t[0], (String)_t[1], this._sharedData);
                        }
                    }

                    if (act!=null) {
                        /* Exit function called irrespective of transition status */
                        act.exit(this._fsm.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                    }
                    
                    FSMStateAction _b = ((FSMState)_f).getAfterTransition();
                    if (_b!=null) {
                        _b.stateTransition(((FSMState)_f).getCurrentState(), 
                                this._sharedData);
                    }
                    
                    break;
                }
            }
        }
        return _r;
    }

    /**
     * Method returns the current state of the FSM<br/>
     * 
     * @return Current state of the FSM
     */
    public String getCurrentState() { return this._fsm.getCurrentState().getCurrentState(); }
    
    /**
     * Method sets the shared data for the FSM<br/>
     * This method overwrites the previous shared data<br/>
     * 
     * @param data  Set shared data for the FSM.<br/>
     *              <b>Note:</b> Call to this function overwrites any previous shared data.
     */
    public void setShareData(Object data) { this._sharedData = data; }
    
    /**
     *
     * @param states
     * @param message
     * @param act
     */
    public void setAction(ArrayList<String> states, String message, 
            FSMAction act) {
        _fsm.setAction(states, message, act);
    }
    
    /**
     *
     * @param state
     * @param message
     * @param act
     */
    public void setAction(String state, String message, 
            FSMAction act) {
        setAction(new ArrayList(Arrays.asList(state)), message, act);
    }
    
    /**
     *
     * @param message
     * @param act
     */
    public void setAction(String message, FSMAction act) {
        _fsm.setAction(message, act);
    }

    public void setStatesBeforeTransition(String state, FSMStateAction act) {
        _fsm.setStateBeforeTransition(state, act);
    }
    
    public void setStatesBeforeTransition(ArrayList<String> states, 
            FSMStateAction act) {
        _fsm.setStateBeforeTransition(states, act);
    }

    public void setStatesBeforeTransition(FSMStateAction act) {
        ArrayList<String> l = null;
        _fsm.setStateBeforeTransition(l, act);
    }
    
    public void setStatesAfterTransition(String state, FSMStateAction act) {
        _fsm.setStateAfterTransition(state, act);
    }
    
    public void setStatesAfterTransition(ArrayList<String> states, 
            FSMStateAction act) {
        _fsm.setStateAfterTransition(states, act);
    }

    public void setStatesAfterTransition(FSMStateAction act) {
        ArrayList<String> l = null;
        _fsm.setStateAfterTransition(l, act);
    }

    /**
     * Method returns all states associated with the FSM<br/>
     * 
     * @return Returns all states of the FSM
     */
    public List getAllStates() { return _fsm.getAllStates(); }
 
    /**
     * 
     * @param act Default Action method for the FSM 
     */
    public void setDefaultFsmAction(FSMAction act) { _role = act; }

    /**
     * Get all "Actions ID", "User Roles", and "Next States" for the "Current State" of a Person.
     * @param stateId
     * @return
     */
    public ArrayList<ArrayList<String>> getStateInformation(String stateId) {

        CustomXMLReader xmlReader = this._fsm.getXmlReader();

        return xmlReader.getStateInformation(stateId);
    }
}
