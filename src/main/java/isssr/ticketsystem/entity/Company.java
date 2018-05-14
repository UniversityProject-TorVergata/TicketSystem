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

    //@Transient
    @OneToMany
    private Collection<CompanyUser> CompanyUserList;

    @Transient
    private List<Product> companyProductList;

    @NotNull
    private String companyName;

    @NotNull
    private String companyAddress;

    @NotNull
    @Column(unique = true)
    private String fiscal_code; //cambiare in partita iva?

    public Company() {
    }

    public Company(@NotNull String companyName, @NotNull String fiscal_code) {
        this.companyName = companyName;
        this.fiscal_code = fiscal_code;
    }

    public void addRegisteredCompanyUser(CompanyUser CompanyUser) {
        this.CompanyUserList.add(CompanyUser);
    }


    public Collection<CompanyUser> getCompanyUserList() {
        return CompanyUserList;
    }

    public void setCompanyUserList(List<CompanyUser> CompanyUserList) {
        this.CompanyUserList = CompanyUserList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void updateCompany(@NotNull Company updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.companyName = updatedData.companyName;
        this.companyAddress = updatedData.companyAddress;
    }

}
