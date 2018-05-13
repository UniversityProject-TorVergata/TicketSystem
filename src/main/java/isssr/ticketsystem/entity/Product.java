package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO change name
@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    private String description;

    private Environment environment;

    //private Company company;

    public Product() {
    }

    public Product(String description, Environment environment, Company company) {
        this.description = description;
        this.environment = environment;
        //this.company = company;
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

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /*public Company getCompany() {
        return company;
    }

    public void setCompany(Company icompany) {
        this.company = company;
    }*/
}
