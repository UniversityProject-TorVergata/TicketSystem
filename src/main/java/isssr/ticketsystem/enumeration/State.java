package isssr.ticketsystem.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum State {

    VALIDATION(2),
    DISPATCHING(2),
    EDIT(3),
    PENDING(1),
    EXECUTION(5),
    ACCEPTANCE(15),
    REOPENED(2),
    CLOSED(0);

    //Misurto in Giorni.
    private int TTL;

    State(int TTL){
        this.TTL = TTL;
    }

    public int getTTL() {
        return TTL;
    }

    public static State getEnum(String stateStr){

        for(State state : State.values()){
            if(stateStr.equals(state.toString()))
                return state;
        }
        return null;


    }


    public static boolean validateState(String stateStr){

        for(State state : State.values()){
            if(stateStr.equals(state.toString()))
                return true;
        }
        return false;

    }
}
