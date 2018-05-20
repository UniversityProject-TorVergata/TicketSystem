package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Enumerated(EnumType.STRING)
    private ProductState productState;
    private String name;
    private String description;
    private double version;

    @ManyToOne
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "target")
    private List<Ticket> ticketList;

    public Product() { }

    public Product(String name, String description, double version) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.productState = ProductState.ACTIVE;
    }

    public Product(Long id,ProductState productState){
        this.id = id;
        this.productState = productState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductState getProductState() {
        return productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    @JsonIgnore
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @JsonProperty
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
        this.ticketList.remove(ticket);
    }

    public void updateProduct(@NotNull Product updatedData) {

        this.name = updatedData.name;
        this.description = updatedData.description;
        this.version = updatedData.version;
        this.company = updatedData.company;
        this.productState = updatedData.productState;
    }

}
