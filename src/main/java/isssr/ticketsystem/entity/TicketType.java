package isssr.ticketsystem.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
/**
 *
 * Categoria assegnata al Ticket.
 *
 */
public enum TicketType {

    //TODO
    BUG,
    CHANGE_REQUEST,
    NEW_IMPLEMENTATION,
    OTHER

}
