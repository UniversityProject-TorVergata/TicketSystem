package isssr.ticketsystem.state_machine.Action;

/**
 * <h1>An abstract implementation of Action for a Finite State Machine (FSM)</h1>
 * 
 * <p>This implementation provides a framework of the Action part of the FSM.<br/>
 * This Class is an abstract class and needs to be implemented to instantiate.<br/>
 * </p>
 * 
 * @version 1.00
 * @author ANKIT
 */
public abstract class FSMAction {
    
    /**
     * 
     * Abstract method; needs to be implemented<br/>
     * 
     * @param curState <br/>
     * This value represents the Current State of the FSM. <br/>
     * @param message <br/>
     * This value specifies the Message for the FSM in Current state. <br/>
     * @param args <br/>
     * This value specifies the argument if any to be passed to the State Node.<br/>
     * @param nextState <br/>
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     * @return  
     */
    public abstract boolean action(String curState, String message, String nextState, Object args);
    
    /**
     * Method is called after successful execution of the FSM transition.<br/>
     * Not mandatory to be implemented; can be over-ridden<br/>
     * 
     * @param curState <br/>
     * This value represents the Current State of the FSM. <br/>
     * @param message <br/>
     * This value specifies the Message for the FSM in Current state. <br/>
     * @param args <br/>
     * This value specifies the argument if any to be passed to the State Node.<br/>
     * @param nextState <br/>
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void afterTransition(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }

    /**
     * Method is called before action method is invoked.<br/>
     * Not mandatory to be implemented; can be over-ridden<br/>
     * 
     * @param curState <br/>
     * This value represents the Current State of the FSM. <br/>
     * @param message <br/>
     * This value specifies the Message for the FSM in Current state. <br/>
     * @param args <br/>
     * This value specifies the argument if any to be passed to the State Node.<br/>
     * @param nextState <br/>
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void entry(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }

    /**
     * Method is called after action method is invoked. This method is invoked<br/>
     * irrespective of transition status.<br/>
     * Not mandatory to be implemented; can be over-ridden<br/>
     * 
     * @param curState <br/>
     * This value represents the Current State of the FSM. <br/>
     * @param message <br/>
     * This value specifies the Message for the FSM in Current state. <br/>
     * @param args <br/>
     * This value specifies the argument if any to be passed to the State Node.<br/>
     * @param nextState <br/>
     * This value specifies the State to be transitioned to; iff, Fsm transition
     * happens.
     */
    public void exit(String curState, String message, String nextState, Object args) {
        /*
         * Can be used by the calling function after transition has taken place
         */
    }
    
}
