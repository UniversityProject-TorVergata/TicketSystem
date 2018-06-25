package isssr.ticketsystem.state_machine.States;

/**
 *
 * @author Ankit
 */
public interface FSMStateAction {
    /**
     *
     * @param arg
     */
    public void stateTransition(String state, Object arg);
}