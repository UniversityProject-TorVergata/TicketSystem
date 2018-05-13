package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_manager")
@Getter
@Setter
public class ProductManager extends CompanyUser {

    public ProductManager(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull Company company) {
        super(fiscal_code, name, surname, email, username, password, company);
    }

    public void updateProductManager(@NotNull ProductManager updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
    }
}
