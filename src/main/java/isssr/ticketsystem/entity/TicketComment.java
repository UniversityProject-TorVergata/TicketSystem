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
@NoArgsConstructor
public class TicketComment extends SystemEvent{



    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    /**
     *
     * @param author Autore del commento al ticket.
     * @param ticket Ticket oggetto del commento.
     * @param comment Testo del commento.
     * @param visibility Visibilitò del commento.
     */
    public TicketComment(RegisteredUser author, Ticket ticket, String comment, Visibility visibility) {
        super(author,comment);
        this.visibility = visibility;
    }


}
