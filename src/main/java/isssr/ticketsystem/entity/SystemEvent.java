package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 *
 * SystemEvent map every action on the system , it is useful for auditing.
 *
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SystemEvent {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private RegisteredUser eventGenerator;

    private String description;

    @ManyToOne
    private Ticket ticket;

    private String timestamp;

    public SystemEvent(RegisteredUser eventGenerator,Ticket ticket, String description) {
        this.eventGenerator = eventGenerator;
        this.description = description;
        this.ticket = ticket;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
    }
}
