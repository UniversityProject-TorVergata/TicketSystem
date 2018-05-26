package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
/**
 * Rappresenta l'amministratore di sistema ha le seguenti responsabilità :
 * <ul>
 *     <li>Assumere personale Interno (Team Coordinator,Team Member,Team Leader)</li>
 *     <li>Registrare Target nel sistema</li>
 * </ul>
 *
 */
public class Admin extends InternalUser {

    public Admin(@NotNull String fiscal_code, @NotNull String name, @NotNull String surname, @NotNull String email,
                 @NotNull String username, @NotNull String password, @NotNull Company company, @NotNull String address) {
        super(fiscal_code, name, surname, email, username, password, company, address);
    }

    /**
     * Metodo usato per aggiornare l'entità con dati ricevuti dal FE.
     * @see isssr.ticketsystem.rest.RegisteredUserRestService
     * @param updatedDataRegisteredUser Un'oggetto ricevuto dal metodo REST con i valori aggiornati da un utente.
     */
    @Override
    public void update(@NotNull RegisteredUser updatedDataRegisteredUser) {

        Admin updatedData = (Admin) updatedDataRegisteredUser;

        this.fiscal_code = updatedData.fiscal_code;
        this.name = updatedData.name;
        this.surname = updatedData.surname;
        this.username = updatedData.username;
        this.email = updatedData.email;
        this.password = updatedData.password;
        this.address = updatedData.address;
    }




}
