package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {


    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketState state;

    @Enumerated(EnumType.STRING)
    private UserType sourceType;


    private String timestamp;

    @Enumerated(EnumType.STRING)
    private TicketType presumedType;

    @Enumerated(EnumType.STRING)
    private TicketType actualType;



    private String title;


    private String description;

    //TODO http://www.devglan.com/spring-boot/file-upload-angularjs-spring-boot-rest
    @Lob
    private byte[] attachedFile;

    private String mediaType;

    @ManyToOne
    private Assistant resolverUser;

    @ManyToOne
    private RegisteredUser openerUser;

    @ManyToOne
    private Product target;

    @Enumerated(EnumType.STRING)
    private Priority customerPriority;

    @Enumerated(EnumType.STRING)
    private Priority actualPriority;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @OneToMany
    private Map<ReletionshipType,RelatedTicket> relationships;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;


    @OneToMany(mappedBy = "ticket")
    private List<SystemEvent> eventRegister;

    @OneToMany(mappedBy = "ticket",targetEntity = SystemEvent.class )
    private List<TicketComment> ticketComments;


    //Costruttore usato per la CRUD utente.
    public Ticket(TicketState state, UserType sourceType ,
                  TicketType presumedType, String title,
                  String description, String attachedByteStream, String attachedByteStreamType,
                  Assistant resolverUser, RegisteredUser openerUser, Product target, Priority customerPriority,
                  Visibility visibility) {
        this.state = state;
        this.sourceType = sourceType;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.presumedType = presumedType;
        this.title = title;
        this.description = description;
        //TODO aggiungere i campi per l'allegato
        //this.attachedByteStream = attachedByteStream;
        //this.attachedByteStreamType = attachedByteStreamType;
        this.resolverUser = resolverUser;
        this.openerUser = openerUser;
        this.target = target;
        this.customerPriority = customerPriority;
        this.visibility = visibility;

    }

    public void updateTicket(Ticket updatedData) {

        //TODO

        this.state = updatedData.state;
        this.sourceType = updatedData.sourceType;
        this.timestamp = updatedData.timestamp;
        this.presumedType = updatedData.presumedType;
        this.actualType = updatedData.actualType;
        this.title = updatedData.title;
        this.description = updatedData.description;
        this.resolverUser = updatedData.resolverUser;
        this.openerUser = updatedData.openerUser;
        this.target = updatedData.target;
        this.customerPriority = updatedData.customerPriority;
        this.actualPriority = updatedData.actualPriority;
        this.visibility = updatedData.visibility;
        this.relationships = updatedData.relationships;
        this.difficulty = updatedData.difficulty;
        this.eventRegister = updatedData.eventRegister;
        this.ticketComments = updatedData.ticketComments;
        //TODO aggiungere i campi per l allegato
        //this.attachedByteStream = updatedData.attachedByteStream;
        //this.attachedByteStreamType = updatedData.attachedByteStreamType;
    }


}
