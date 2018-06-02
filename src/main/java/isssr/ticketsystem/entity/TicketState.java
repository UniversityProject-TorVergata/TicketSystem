package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Rappresenta gli stati di un Ticket all'interno del sistema.
 * <ul>
 *     <li>New : Appena inserito dall'utente</li>
 *     <li>Pending : Deve essere assegnato dal Team Coordinator a un Team</li>
 *     <li>Execution : Ticket in fase di risoluzione</li>
 *     <li>Trashed : Ticket cestinato</li>
 * </ul>
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TicketState {

    NEW("NEW"),
    PENDING("PENDING"),
    READY("READY"),
    EXECUTION("EXECUTION"),
    TRASHED("TRASHED"); //Per i ticket cestinati.

    private final String name;

    private TicketState(String name){
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
