package isssr.ticketsystem.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Priorità assegnata ad un Ticket da Customer e/o Personale Interno.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Priority {
    LOW,
    AVERAGE,
    HIGH
}
