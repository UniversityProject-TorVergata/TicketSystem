package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    /*
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private Collection<InternalUser> CompanyUserList;

    @JsonIgnore
    @OneToMany(mappedBy =  "company")
    private List<Target> companyProductList;
    */
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

    public Company(@NotNull String companyName, @NotNull String companyAddress, @NotNull String fiscal_code) {
        this.companyName = companyName;
        this.fiscal_code = fiscal_code;
        this.companyAddress = companyAddress;
    }



    /*
    public void addRegisteredCompanyUser(InternalUser CompanyUser) {
        this.CompanyUserList.add(CompanyUser);
    }

    @JsonIgnore
    public Collection<InternalUser> getCompanyUserList() {
        return CompanyUserList;
    }
    @JsonProperty
    public void setCompanyUserList(Collection<InternalUser> companyUserList) {
        CompanyUserList = companyUserList;
    }

    @JsonIgnore
    public List<Target> getCompanyProductList() {
        return companyProductList;
    }

    @JsonProperty
    public void setCompanyProductList(List<Target> companyProductList) {
        this.companyProductList = companyProductList;
    }
*/
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
