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
 *
 * This class is used to group tickets related it is necessary to save
 * the Map<RelationshipType,List<Ticket>> in the DB.
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
