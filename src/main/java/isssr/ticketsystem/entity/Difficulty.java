package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Difficolta' di risoluzione di un Ticket cosi' come indicata dal Team Coordinator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Difficulty {

    LOW,
    MEDIUM,
    HIGH
}
