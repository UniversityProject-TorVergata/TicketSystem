package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@MappedSuperclass
@Entity
@Table(name = "company_user")
@Getter
@Setter
public abstract class CompanyUser extends RegisteredUser {

    private Long idCompanyUser;


    /*
    @Transient
    private Long idCompany;
    */

    public CompanyUser(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String username, @NotNull String password, @NotNull Company company) {
        super(fiscal_code, name, surname, email, username, password, company);
    }

    public CompanyUser() {
    }

    public Long getIdCompanyUser() {
        return idCompanyUser;
    }

    public void setIdCompanyUser(Long idCompanyUser) {
        this.idCompanyUser = idCompanyUser;
    }

    /*
    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }*/

    public void updateCompanyUser(@NotNull CompanyUser updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
    }
}