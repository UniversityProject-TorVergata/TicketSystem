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
    protected Long id;

    @ManyToOne
    protected RegisteredUser eventGenerator;

    protected String description;



    protected String timestamp;



    /**
     * Costruttore
     *
     * @param eventGenerator L'utente che ha generato l'evento
     * @param description descrizione dell'evento.
     */
    public SystemEvent(RegisteredUser eventGenerator, String description) {
        this.eventGenerator = eventGenerator;
        this.description = description;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
    }
}
