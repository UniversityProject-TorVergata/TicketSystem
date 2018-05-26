package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
/**
 * Visibilità di un Ticket ai Customer diversi dall'apertore.
 *
 */
public enum Visibility {

    PUBLIC,
    PRIVATE
}
