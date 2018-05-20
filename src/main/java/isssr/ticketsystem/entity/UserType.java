package isssr.ticketsystem.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserType {
    CUSTOMER,
    HELP_DESK_CUSTOMER,
    SYSTEM

}
