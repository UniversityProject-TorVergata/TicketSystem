package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 *
 * This class represents a comment to a specified ticket and is a specialization of a system event.
 *
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketComment extends SystemEvent{



    @Enumerated(EnumType.STRING)
    private Visibility visibility;


    public TicketComment(RegisteredUser author, Ticket ticket, String comment, Visibility visibility) {
        super(author,ticket,comment);
        this.visibility = visibility;
    }
}
