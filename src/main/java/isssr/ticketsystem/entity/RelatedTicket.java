package isssr.ticketsystem.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Questa classe e' usata per raggruppare ticket relazionati.
 * @see Ticket
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RelatedTicket {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Ticket> ticketList;
}
