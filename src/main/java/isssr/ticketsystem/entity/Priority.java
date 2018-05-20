package isssr.ticketsystem.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Priority {
    LOW,
    AVERAGE,
    HIGH
}
