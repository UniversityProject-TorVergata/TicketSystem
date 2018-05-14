package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private String title;

    private String description; // Oppure Enumerazione ?

    private String byteStream;

    private String byteStreamType;

    //@Transient
    @ManyToOne
    private CompanyUser resolverUser; //companyUser

    //Aggiunto da AlessioDL
    @ManyToOne
    private RegisteredUser openerUser;
    //Aggiunto da AlessioDL
    @ManyToOne
    private Product product;

    public Ticket() {
    }

    //Aggiunto da AlessioDL
    public Ticket(String title, String description, String byteStream, String byteStreamType, CompanyUser resolverUser, RegisteredUser openerUser, Product product) {
        this.title = title;
        this.description = description;
        this.byteStream = byteStream;
        this.byteStreamType = byteStreamType;
        this.resolverUser = resolverUser;
        this.openerUser = openerUser;
        this.product = product;
    }

    public Ticket(String title, String description, String byteStream, String byteStreamType, CompanyUser resolverUser) {
        this.title = title;
        this.description = description;
        this.byteStream = byteStream;
        this.byteStreamType = byteStreamType;
        this.resolverUser = resolverUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getByteStream() {
        return byteStream;
    }

    public void setByteStream(String byteStream) {
        this.byteStream = byteStream;
    }

    public String getByteStreamType() {
        return byteStreamType;
    }

    public void setByteStreamType(String byteStreamType) {
        this.byteStreamType = byteStreamType;
    }

    public CompanyUser getCompanyUser() {
        return resolverUser;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.resolverUser = companyUser;
    }

    public void updateTicket(@NotNull Ticket updatedData) {

        this.title = updatedData.title;
        this.description = updatedData.description;
        this.byteStream = updatedData.byteStream;
        this.byteStreamType = updatedData.byteStreamType;
        this.resolverUser = updatedData.resolverUser; //companyUser
        //Aggiunti da Alessio DL
        this.product = updatedData.product;
        this.openerUser = updatedData.openerUser;
    }
}
