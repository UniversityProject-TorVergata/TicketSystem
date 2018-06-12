package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.TeamCoordinator;
import isssr.ticketsystem.entity.TeamLeader;
import isssr.ticketsystem.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RegisteredUserDao extends JpaRepository<RegisteredUser, Long> {

    /**
     * Metodo Query che seleziona un Utente dal Database dati la sua password e il suo username,
     * usato per il login
     *
     * @param username
     * @param password
     * @return L'utente identificato (se presente).
     */
    @Query("select u from RegisteredUser u where u.username = :username and u.password = :password")
    RegisteredUser getUserByLogin(@Param("username") String username,@Param("password") String password);

    /**
     * Metodo query che seleziona tutti i TeamLeader nel Database.
     *
     * @return lista di TeamLeader
     */
    @Query("select tl from TeamLeader tl ")
    List<TeamLeader> getListTeamLeader();


    /**
     * Metodo query che seleziona tutti i TeamMember senza Team,ovvero quelli che al momento
     * sono "disoccupati"
     *
     * @return Lista di TeamMember "disoccupati".
     */
    @Query("select tm from TeamMember tm where tm.team = null")
    List<TeamMember> getListFreeTeamMember();

    @Query("select tc from TeamCoordinator tc")
    TeamCoordinator  getTeamCoordinator();




}
