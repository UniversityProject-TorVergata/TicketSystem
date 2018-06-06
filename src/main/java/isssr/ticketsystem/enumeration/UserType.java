package isssr.ticketsystem.enumeration;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * Tipo di utente che apre il Ticket.
 * <ul>
 *     <li>Customer : Cliente che apre il ticket autonomamente connettendosi al sistema.</li>
 *     <li>Help Desk Customer : impiegato di Call Center che apre il Ticket per conto di un cliente</li>
 *     <li>Sistema : genera speciali Ticket di Alert</li>
 * </ul>
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserType {
    CUSTOMER,
    HELP_DESK_CUSTOMER,
    SYSTEM

}
