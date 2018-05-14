package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue // Autoincrement
    private Long idCompany;

    @Transient
    private Collection<RegisteredUser> registeredUserList;

    //Aggiunto da AlessioDL
    @Transient
    private List<Product> companyProductList;

    @NotNull
    private String companyName;

    @NotNull
    @Column(unique = true)
    private String fiscal_code; //cambiare in partita iva?

    public Company() {
    }

    public Company(@NotNull String companyName, @NotNull String fiscal_code) {
        this.companyName = companyName;
        this.fiscal_code = fiscal_code;
    }

    public void addRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUserList.add(registeredUser);
    }


    public Collection<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void updateCompany(@NotNull Company updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.companyName = updatedData.companyName;


    }
}
