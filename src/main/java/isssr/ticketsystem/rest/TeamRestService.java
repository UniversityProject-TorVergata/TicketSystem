package isssr.ticketsystem.rest;


import isssr.ticketsystem.controller.TeamController;
import isssr.ticketsystem.entity.Team;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamRestService {


    @Autowired
    private TeamController teamController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Team> teamProduct(@RequestBody Team team) {
        Team createdTeam = teamController.insertTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

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

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Team> findTeam(@PathVariable Long id) {
        Team teamFound = teamController.findTeamById(id);
        return new ResponseEntity<>(teamFound, teamFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deletedTeam = teamController.deleteTeam(id);
        return new ResponseEntity<>(deletedTeam, deletedTeam ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> getProduct() {
        List<Team> team = teamController.getTeams();
        return new ResponseEntity<>(team, HttpStatus.OK);
    }



}
