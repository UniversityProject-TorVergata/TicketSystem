package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Un Target non può essere cancellato dal DataBase.
 * Può però essere "ritirato" ,questa enumerazione rappresenta
 * i due stati del Target "ATTIVO" e "RITIRATO".
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TargetState {

    RETIRED,
    ACTIVE
}
