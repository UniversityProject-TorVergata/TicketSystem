package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@Getter
@Setter
public class Ticket {

    /**
     * Attributes defined as database's columns.
     */
    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    /*@NotNull
    private Product product_name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TicketType tycket_type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityType activity_type;

    @Enumerated(EnumType.STRING)
    private ThirdParty third_party;

    private String description;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    //TODO File come stringa di byte
    //TODO Tipo di file (image, pdf, ...) o non riesco a ritornare il file dalla stringa

    //private Ticket attached_ticket;


    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private String created_at;*/

    public Ticket() {
    }
}
