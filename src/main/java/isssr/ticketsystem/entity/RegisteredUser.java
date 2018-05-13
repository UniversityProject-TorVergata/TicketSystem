package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;

@Table(name = "registered_user")
@Getter
@Setter
@MappedSuperclass
public abstract class RegisteredUser {

    /**
     * Attributes defined as database's columns.
     */

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    @NotNull
    @Column(unique = true)
    protected String fiscal_code;

    @NotNull
    protected String name;

    @NotNull
    protected String surname;

    @NotNull
    @Column(unique = true)
    protected String email;

    @NotNull
    @Column(unique = true)
    protected String username;

    @NotNull
    protected String password;

    protected String created_at;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Transient
    protected Company company;

    public RegisteredUser() {
    }

    public RegisteredUser(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname,
                          @NotNull String email, @NotNull String username, @NotNull String password,
                          @NotNull Company company) {
        this.fiscal_code = fiscal_code;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        this.company = company;
    }

    /*
        Get and Set functions
     */

    public String getFiscal_code() {
        return fiscal_code;
    }

    public void setFiscal_code(String fiscal_code) {
        this.fiscal_code = fiscal_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void updateRegisteredUser(@NotNull RegisteredUser updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.email = updatedData.email;
        this.username = updatedData.username;
        this.password = updatedData.password;
    }
}
