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
@Table(name = "internal_user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "Admin"),
        @JsonSubTypes.Type(value = TeamMember.class, name = "TeamMember") ,
        @JsonSubTypes.Type(value = TeamCoordinator.class, name = "TeamCoordinator") }
)

/**
 * Superclasse che rappresenta il personale interno al sistema che si occupa,
 * di gestione della piattaforma e dei Ticket.
 *
 */
public abstract class InternalUser extends RegisteredUser {

    /*
       Se company non fa più parte dell architettura del sistema ,che bisogno c'è di questa superclasse?

     */

    private Long idCompanyUser;

    @ManyToOne
    private Company company;


    public InternalUser() { }

    public InternalUser(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
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

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedDataRegisteredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser updatedDataRegisteredUser) {

        InternalUser updatedData = (InternalUser) updatedDataRegisteredUser;
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
