package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Enumerazione che rappresenta il tipo di un Target.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TargetType {

    Product,
    Service
}
