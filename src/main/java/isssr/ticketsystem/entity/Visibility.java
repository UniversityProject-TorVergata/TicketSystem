package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * Visibilita' di un Ticket ai Customer diversi dall'apertore.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Visibility {

    PUBLIC,
    PRIVATE
}
