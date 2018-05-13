package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Entity
@Table(name = "third_party_customer")
@Getter
@Setter
public class ThirdPartyCustomer extends CompanyUser {

    //Aggiunto da AlessioDL
    @OneToMany
    private Collection<Product> boughtProductList;
    //Aggiunto da AlessioDL
    public ThirdPartyCustomer(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull Company company, Collection<Product> boughtProductList) {
        super(fiscal_code, name, surname, email, username, password, company);
        this.boughtProductList = boughtProductList;
    }

    public ThirdPartyCustomer(Collection<Product> boughtProductList) {
        this.boughtProductList = boughtProductList;
    }

    public ThirdPartyCustomer(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull Company company) {
        super(fiscal_code, name, surname, email, username, password, company);
    }

    public ThirdPartyCustomer() {
    }

    public void updateThirdPartyCustomer(@NotNull ThirdPartyCustomer updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        //Aggiunto da AlessioDL
        this.boughtProductList = updatedData.boughtProductList;
    }
}
