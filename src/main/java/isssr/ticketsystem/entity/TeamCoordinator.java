package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Il Team Coordinator che fa parte del personale interno ha le seguenti responsabilita'
 * <ul>
 *     <li>Creare Team</li>
 *     <li>Assegnare Ticket al Team Leader di un Team</li>
 *     <li>Assegnare i Team Member a un Team</li>
 *     <li>Modificare informazioni presenti su un Ticket e in particolare aggiungerne</li>
 *     <li>Cestinare un Ticket</li>
 *     <li>Indicare Priorita' e Tipologia effettiva di un Ticket</li>
 * </ul>
 *
 */
@Entity
@Table(name = "team_coordinator")
@Getter
@Setter
@SuppressWarnings("unused")
public class TeamCoordinator extends InternalUser {
    /*
    Il Team Coordinator gestisce tutti i prodotti nel sistema
    @OneToOne
    private Target managedTarget;
    */
    public TeamCoordinator() { }

}
