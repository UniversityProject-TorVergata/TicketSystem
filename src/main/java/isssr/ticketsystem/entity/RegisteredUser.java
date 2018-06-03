package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;


/**
 * Superclasse di tutte le entita' di Utente all'interno del sistema.
 * Contiene gli attributi caratterizzanti di un utente.
 *
 */
@Table(name = "registered_user")
@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value =Customer.class, name = "Customer"),

        @JsonSubTypes.Type(value = InternalUser.class, name = "InternalUser") }
)
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

    @NotNull
    protected String address;

    protected String created_at; // Data di creazione dell'utente.

    public RegisteredUser() { }

    public RegisteredUser(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname,
                          @NotNull String email, @NotNull String username, @NotNull String password, @NotNull String address) {
        this.fiscal_code = fiscal_code;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }


    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedData Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    public void update(@NotNull RegisteredUser updatedData) {

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.email = updatedData.email;
        this.username = updatedData.username;
        this.password = updatedData.password;
        this.address = updatedData.address;
    }
}
