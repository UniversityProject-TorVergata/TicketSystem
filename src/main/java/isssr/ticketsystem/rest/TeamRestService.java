package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.TeamController;
import isssr.ticketsystem.entity.ProblemArea;
import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.entity.TeamLeader;
import isssr.ticketsystem.entity.TeamMember;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("team")
 * attraverso i metodi definiti nella classe TeamController.
 */

@RestController
@RequestMapping("/team")
public class TeamRestService {


    @Autowired
    private TeamController teamController;


    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team viene inserito nel DB.
     * @param team team che va aggiunto al DB.
     * @return info del team aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Team> teamProduct(@RequestBody Team team) {
        Team createdTeam = teamController.insertTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
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
        Team updatedTeam;
        try {
            updatedTeam =  teamController.updateTeam(id, team);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(team, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team indicato viene restituito.
     * @param id Id del team le cui info vanno visualizzate.
     * @return info del team + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Team> findTeam(@PathVariable Long id) {
        Team teamFound = teamController.findTeamById(id);
        return new ResponseEntity<>(teamFound, teamFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    /**
     * Metodo usato per la gestione di una DELETE che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo il team specificato viene cancellato dal DB.
     * @param id Id del team che va cancellato dal DB.
     * @return info del team cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deletedTeam = teamController.deleteTeam(id);
        return new ResponseEntity<>(deletedTeam, deletedTeam ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restituiti tutti i team presenti nel DB.
     * @return team presenti nel DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.TeamController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> getProduct() {
        List<Team> team = teamController.getTeams();
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    /**
     * Ricerca di tutti i TeamMember di un Team
     *
     * @param id ID del team di cui interessano i TeamMember
     * @return Lista dei TeamMember del Team
     */
    @RequestMapping(path = "/team_member/{id}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TeamMember>> getTeamMemberByTeamId(@PathVariable Long id) {
        Collection<TeamMember> listTeamMember = teamController.getTeamMemberByTeamId(id);
        if(listTeamMember != null)
            return new ResponseEntity<>(listTeamMember,HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamMember,HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(listTeamMember,HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamMember,HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(updatedTeamMember, updatedTeamMember == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);

    }

    /**
     * Modifica/Aggiunta di una ProblemArea ad un Team
     *
     * @see ProblemArea
     * @param id  ID del Team da modificare
     * @param problemArea ProblemArea da inserire
     * @return L'oggetto Team aggiornato con l'aggiunta/modifica della ProblemArea
     */
    @RequestMapping(path = "/problem_area/{id}/{problemArea}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateProblemAreaTeam(@PathVariable Long id, @PathVariable ProblemArea problemArea){
        Team updatedTeam = teamController.updateProblemArea(id, problemArea);
        return new ResponseEntity<>(updatedTeam, updatedTeam == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);

    }


}
