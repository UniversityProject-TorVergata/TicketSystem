package isssr.ticketsystem.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Un Target non puo' essere cancellato dal DataBase.
 * Puo' pero' essere "ritirato" ,questa enumerazione rappresenta
 * i due stati del Target "ATTIVO" e "RITIRATO".
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TargetState {

    RETIRED,
    ACTIVE
}
