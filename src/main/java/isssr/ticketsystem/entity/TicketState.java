package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Rappresenta gli stati di un Ticket all'interno del sistema.
 * <ul>
 *     <li>New : Appena inserito dall'utente</li>
 *     <li>Pending : Deve essere assegnato dal Team Coordinator a un Team</li>
 *     <li>Ready : Assegnato a un Team ma non ancora preso in carico</li>
 *     <li>Execution : Ticket in fase di risoluzione</li>
 *     <li>Trashed : Ticket cestinato</li>
 * </ul>
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TicketState {

    NEW,
    PENDING,

    READY,
    EXECUTION,
    TRASHED //Per i ticket cestinati.

}
