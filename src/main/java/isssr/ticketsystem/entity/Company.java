package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company")
@Getter
@Setter
public abstract class Company extends RegisteredUser {

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    private Long id_registered_user;

    private String companyName;

    public Company() {
    }

    public Company(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull String companyName) {
        super(fiscal_code, name, surname, email, username, password);
        this.id_registered_user = super.getId();
        this.companyName = companyName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_registered_user() {
        return id_registered_user;
    }

    public void setId_registered_user(Long id_registered_user) {
        this.id_registered_user = id_registered_user;
    }

    public void updateCompany(@NotNull RegisteredUser updatedData, @NotNull Company company) {

        this.fiscal_code = updatedData.getFiscal_code();
        this.name = updatedData.getName();
        this.surname = updatedData.getSurname();
        this.email = updatedData.getEmail();
        this.username = updatedData.getUsername();
        this.password = updatedData.getPassword();
        this.companyName = company.companyName;

    }
}
