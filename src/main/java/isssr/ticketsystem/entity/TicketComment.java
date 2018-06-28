package isssr.ticketsystem.entity;

import isssr.ticketsystem.enumeration.Visibility;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * Questa classe rappresenta un commento a uno specifico ticket.
 *
 */

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
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

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisteredUser getEventGenerator() {
        return eventGenerator;
    }

    public void setEventGenerator(RegisteredUser eventGenerator) {
        this.eventGenerator = eventGenerator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
