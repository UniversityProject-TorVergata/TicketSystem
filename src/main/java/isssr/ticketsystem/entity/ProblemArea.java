package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Area di problemi risolti da un Team.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ProblemArea {

    AREA1,
    AREA2,
    AREA3
}
