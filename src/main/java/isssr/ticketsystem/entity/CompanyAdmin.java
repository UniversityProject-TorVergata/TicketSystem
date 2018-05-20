package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company_admin")
@Getter
@Setter
@NoArgsConstructor
public class CompanyAdmin extends CompanyUser {

    public CompanyAdmin(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                        @NotNull String username, @NotNull String password, @NotNull Company company, @NotNull String address) {
        super(fiscal_code, name, surname, email, username, password, company, address);
    }

    public void updateCompanyAdmin(@NotNull CompanyAdmin updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.address = updatedData.address;
    }


}
