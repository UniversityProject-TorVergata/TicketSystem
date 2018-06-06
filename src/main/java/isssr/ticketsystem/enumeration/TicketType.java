package isssr.ticketsystem.enumeration;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 *
 * Categoria assegnata al Ticket.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TicketType {

    //TODO
    BUG,
    CHANGE_REQUEST,
    NEW_IMPLEMENTATION,
    OTHER

}
