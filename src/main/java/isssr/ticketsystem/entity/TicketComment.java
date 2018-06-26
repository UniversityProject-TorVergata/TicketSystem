package isssr.ticketsystem.entity;

import isssr.ticketsystem.enumeration.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * Questa classe rappresenta un commento a uno specifico ticket.
 *
 */

@Entity
@Getter
@Setter
public class TicketComment{



    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private RegisteredUser eventGenerator;

    private String description;

    private String timestamp;


    public TicketComment() {

    }
}
