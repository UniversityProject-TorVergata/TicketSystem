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
    private String fiscal_code;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String address;

    public RegisteredUser() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
