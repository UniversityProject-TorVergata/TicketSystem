package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.TeamController;
import isssr.ticketsystem.entity.TeamLeader;
import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("team")
 * attraverso i metodi definiti nella classe TeamController.
 */

@RestController
@RequestMapping("/team")
@SuppressWarnings("ConstantConditions")
public class TeamRestService {


    private final TeamController teamController;

    @Autowired
    public TeamRestService(TeamController teamController) {
        this.teamController = teamController;
    }


    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team viene inserito nel DB.
     * @param team team che va aggiunto al DB.
     * @return info del team aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Team> insertTeam(@RequestBody Team team) {
        Team createdTeam = teamController.insertTeam(team);
        if(createdTeam != null)
            return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(createdTeam, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team indicato viene aggiornato nel DB con le info specificate.
     * @param id Id del team che va aggiornato.
     * @param team Info aggiornate del team.
     * @return team eventualmente aggiornato + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        Team updatedTeam = teamController.updateTeam(id, team);
        if(updatedTeam != null)
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTeam, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una DELETE che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team specificato viene cancellato dal DB.
     * @param id Id del team che va cancellato dal DB.
     * @return info del team cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTeamById(@PathVariable Long id) {
        boolean deletedTeam = teamController.deleteTeam(id);
        if(deletedTeam)
            return new ResponseEntity<>(deletedTeam, HttpStatus.OK);
        else
            return new ResponseEntity<>(deletedTeam, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restituiti tutti i team presenti nel DB.
     * @return team presenti nel DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamController.getTeams();
        if(teams != null)
            return new ResponseEntity<>(teams, HttpStatus.OK);
        else
            return new ResponseEntity<>(teams, HttpStatus.NOT_FOUND);
    }

    /**
     * Ricerca di tutti i TeamMember di un Team
     *
     * @param id Id del team di cui interessano i TeamMember
     * @return Lista dei TeamMember del Team
     */
    @RequestMapping(path = "/team_member/{id}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TeamMember>> getTeamMemberByTeamId(@PathVariable Long id) {
        Collection<TeamMember> listTeamMember = teamController.getTeamMemberByTeamId(id);
        if(listTeamMember != null)
            return new ResponseEntity<>(listTeamMember, HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamMember, HttpStatus.NOT_FOUND);
    }

    /**
     * Ricerca il TeamLeader di un Team
     *
     * @param id ID del team di cui interessa il TeamLeader
     * @return Lista dei TeamMember del Team
     */
    @RequestMapping(path = "/team_leader/{id}", method = RequestMethod.GET)
    public ResponseEntity<TeamLeader> getTeamLeaderByTeamId(@PathVariable Long id) {
        TeamLeader teamLeader= teamController.getTeamLeaderByTeamId(id);
        if(teamLeader != null)
            return new ResponseEntity<>(teamLeader, HttpStatus.OK);
        else
            return new ResponseEntity<>(teamLeader, HttpStatus.NOT_FOUND);
    }

    /**
     * Ricerca di tutti i TeamMember assistenti di un TeamLeader
     *
     * @param id ID del TeamLeader di cui servono gli assistenti
     * @return Lista dei TeamMember assistenti del TeamLeader
     */
    @RequestMapping(path = "/team_member/team_leader/{id}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TeamMember>> getTeamMemberByTeamLeaderId(@PathVariable Long id) {
        Collection<TeamMember> listTeamMember = teamController.getTeamMemberByTeamLeaderId(id);
        if(listTeamMember != null)
            return new ResponseEntity<>(listTeamMember, HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamMember, HttpStatus.NOT_FOUND);
    }


    /**
     * Aggiunta di un TeamMember ad un Team
     *
     * @param teamID ID del Team destinazione.
     * @param teamMemberID Id del  TeamMember da Aggiungere al Team
     * @return l'oggetto TeamMember aggiornato con l 'aggiunta del Team
     */
    @RequestMapping(path = "/add_team_member/{teamID}/{teamMemberID}", method = RequestMethod.PUT)
    public ResponseEntity<TeamMember> addTeamMember(@PathVariable("teamID") Long teamID, @PathVariable("teamMemberID") Long teamMemberID){
        TeamMember updatedTeamMember = teamController.addTeamMember(teamID,teamMemberID);
        if(updatedTeamMember != null)
            return new ResponseEntity<>(updatedTeamMember, HttpStatus.OK);
        else
            return new ResponseEntity<>(updatedTeamMember, HttpStatus.NOT_FOUND);

    }
}
