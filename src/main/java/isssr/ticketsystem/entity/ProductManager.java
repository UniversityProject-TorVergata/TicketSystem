package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_manager")
@Getter
@Setter
public class ProductManager extends CompanyUser {

    @OneToOne
    private Product managedProduct;

    public ProductManager() { }

    public ProductManager(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                          @NotNull String username, @NotNull String password, @NotNull Company company, Product managerdProduct, @NotNull String address) {
        super(fiscal_code, name, surname, email, username, password, company, address);
        this.managedProduct = managerdProduct;
    }

    public void updateProductManager(@NotNull ProductManager updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.managedProduct = updatedData.managedProduct;
        this.address = updatedData.address;
    }

}
