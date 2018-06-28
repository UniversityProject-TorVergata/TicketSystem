package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Il Team Leader e' un TeamMember posto alla guida del Team a cui appartiene
 * ha le seguenti responsabilita' :
 * <ul>
 *     <li>Eseguire il pull sulla coda di Ticket assegnati dal Team Coordinator</li>
 *     <li>Assegnare ticket tra i Team Member del suo Team</li>
 * </ul>
 *
 */
@Entity
@Table(name = "team_leader")
@Getter
@Setter
@SuppressWarnings("unused")
public class TeamLeader extends TeamMember {


    public TeamLeader() { }


}
