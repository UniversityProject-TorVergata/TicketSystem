package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@MappedSuperclass
@Entity
@Table(name = "company_user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompanyAdmin.class, name = "CompanyAdmin"),
        @JsonSubTypes.Type(value = Assistant.class, name = "Assistant") ,
        @JsonSubTypes.Type(value = ProductManager.class, name = "ProductManager") }
)
public abstract class CompanyUser extends RegisteredUser {

    private Long idCompanyUser;

    @ManyToOne
    private Company company;

    public CompanyUser() { }

    public CompanyUser(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                       @NotNull String username, @NotNull String password, @NotNull Company company, @NotNull String address) {
        super(fiscal_code, name, surname, email, username, password, address);
        this.company = company;
    }

    public Long getIdCompanyUser() {
        return idCompanyUser;
    }

    public void setIdCompanyUser(Long idCompanyUser) {
        this.idCompanyUser = idCompanyUser;
    }

    public void updateCompanyUser(@NotNull CompanyUser updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.company = updatedData.company;
        this.address = updatedData.address;
    }
}
