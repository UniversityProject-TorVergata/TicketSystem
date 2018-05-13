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

    private String description;

    private String byteStream;

    private String byteStreamType;

    @Transient
    private CompanyUser companyUser;

    public Ticket() {
    }

    public Ticket(String title, String description, String byteStream, String byteStreamType, CompanyUser companyUser) {
        this.title = title;
        this.description = description;
        this.byteStream = byteStream;
        this.byteStreamType = byteStreamType;
        this.companyUser = companyUser;
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
        return companyUser;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.companyUser = companyUser;
    }

    public void updateTicket(@NotNull Ticket updatedData) {

        this.title = updatedData.title;
        this.description = updatedData.description;
        this.byteStream = updatedData.byteStream;
        this.byteStreamType = updatedData.byteStreamType;
        this.companyUser = updatedData.companyUser;
    }
}
