package isssr.ticketsystem.enumeration;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Priorita' assegnata ad un Ticket da Customer e/o Personale Interno.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Priority {
    LOW,
    AVERAGE,
    HIGH
}
