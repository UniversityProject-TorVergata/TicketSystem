package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * SystemEvent rappresenta un'azione sul sistema ed e' usato per l'auditing.
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


    /**
     * Costruttore
     *
     * @param eventGenerator L'utente che ha generato l'evento
     * @param ticket Il ticket oggetto dell'evento (se presente)
     * @param description descrizione dell'evento.
     */
    public SystemEvent(RegisteredUser eventGenerator,Ticket ticket, String description) {
        this.eventGenerator = eventGenerator;
        this.description = description;
        this.ticket = ticket;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
    }
}
