package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    private String name;
    private String description;

    //Aggiunto da AlessioDL
    @ManyToOne
    private Company company;


    @Transient
    private Collection<ThirdPartyCustomer> thirdPartyCustomer;

    @Transient
    private List<Ticket> ticketList;

    public Product() {
    }

    //Aggiunto da AlessioDL
    public Product(String name, String description, Company company, Collection<ThirdPartyCustomer> thirdPartyCustomer, List<Ticket> ticketList) {
        this.name = name;
        this.description = description;
        this.company = company;
        this.thirdPartyCustomer = thirdPartyCustomer;
        this.ticketList = ticketList;
    }

    public Product(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ThirdPartyCustomer> getThirdPartyCustomer() {
        return thirdPartyCustomer;
    }

    public void setThirdPartyCustomer(Collection<ThirdPartyCustomer> thirdPartyCustomer) {
        this.thirdPartyCustomer = thirdPartyCustomer;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public void updateProduct(@NotNull Product updatedData) {

        this.name = updatedData.name;
        this.description = updatedData.description;
        //Aggiunto da AlessioDL
        this.company = updatedData.company;
    }
}
