package isssr.ticketsystem.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TicketType {

    BUG,
    CHANGE_REQUEST,
    NEW_IMPLEMENTATION,
    OTHER

}
