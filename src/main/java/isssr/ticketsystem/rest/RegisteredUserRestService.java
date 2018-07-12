package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("registered_user")
 * attraverso i metodi definiti nella classe RegisteredUserController.
 */
@SuppressWarnings("ConstantConditions")
@RestController
@RequestMapping(path = "registered_user")
public class RegisteredUserRestService {

    private final RegisteredUserController registeredUserController;

    @Autowired
    public RegisteredUserRestService(RegisteredUserController registeredUserController) {
        this.registeredUserController = registeredUserController;
    }

    /**
     * Metodo usato per la gestione di una POST che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo l'utente viene inserito nel DB.
     * @param registeredUser utente che va aggiunto al DB.
     * @return info dell'utente aggiunto al DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.RegisteredUserController
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RegisteredUser> insertRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        RegisteredUser createdRegisteredUser = registeredUserController.insertRegisteredUser(registeredUser);
        return new ResponseEntity<>(createdRegisteredUser, HttpStatus.CREATED);
    }

    /**
     * Metodo usato per la gestione di una PUT che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo le info aggiornate relative all'utente specificato
     * vengono inserite nel DB.
     * @param id Id dell'utente da aggiornare.
     * @param registeredUser info aggiornate dell'utente.
     * @return utente eventualmente aggiornato + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.RegisteredUserController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<RegisteredUser> updateRegisteredUser(@PathVariable Long id, @RequestBody RegisteredUser registeredUser) {
        RegisteredUser updatedRegisteredUser = registeredUserController.updateRegisteredUser(id, registeredUser);
        if(updatedRegisteredUser != null)
            return new ResponseEntity<>(updatedRegisteredUser, HttpStatus.OK);
        return new ResponseEntity<>(updatedRegisteredUser, HttpStatus.NOT_FOUND);
    }


     /**
     * Metodo usato per la gestione di una Delete che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo l'utente specificato viene cancellato dal DB.
     * @param id Id dell'utente che va cancellato dal DB.
     * @return utente cancellato dal DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.RegisteredUserController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteRegisteredUser(@PathVariable Long id) {
        boolean deletedRegisteredUser = registeredUserController.deleteRegisteredUser(id);
        if(deletedRegisteredUser)
            return new ResponseEntity<>(deletedRegisteredUser, HttpStatus.OK);
        else
            return new ResponseEntity<>(deletedRegisteredUser,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione del login di un utente precedentemente registrato.
     *
     * @param loginBean oggetto contenente username e password dell'utente che vuole loggarsi.
     * @return esito del login
     */
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RegisteredUser> getRegisteredUserByLogin(@RequestBody LoginBean loginBean){
        RegisteredUser userLogged = registeredUserController.getRegisteredUserByLogin(loginBean.getUsername(),loginBean.getPassword());
        if(userLogged != null)
            return new ResponseEntity<>(userLogged,HttpStatus.OK);
        return new ResponseEntity<>(userLogged,HttpStatus.NOT_FOUND);

    }


    /**
     * Metodo usato per la richiesta della lista dei TeamLeader con associatio un team del sistema
     *
     * @return la lista dei TeamLeader con associato un team del sistema
     */
    @RequestMapping(path = "/employed_team_leader", method = RequestMethod.GET)
    public ResponseEntity<List<TeamLeader>> getListEmployedTeamLeader() {
        List<TeamLeader> listTeamLeader = registeredUserController.getListEmployedTeamLeader();
        if(listTeamLeader != null)
            return new ResponseEntity<>(listTeamLeader,HttpStatus.OK);
        return new ResponseEntity<>(listTeamLeader,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la richiesta della lista dei TeamLeader senza team del sistema
     *
     * @return la lista dei TeamLeader senza team  del sistema
     */
    @RequestMapping(path = "/free_team_leader", method = RequestMethod.GET)
    public ResponseEntity<List<TeamLeader>> getListFreeTeamLeader() {
        List<TeamLeader> listTeamLeader = registeredUserController.getListFreeEmployedTeamLeader();
        if(listTeamLeader != null)
            return new ResponseEntity<>(listTeamLeader,HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamLeader,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la richiesta della lista dei TeamMember senza Team ("Disoccupati") del sistema.
     *
     * @return lista dei TeamMember senza Team
     */
    @RequestMapping(path = "/free_team_member", method = RequestMethod.GET)
    public ResponseEntity<List<TeamMember>> getListFreeTeamMember() {
        List<TeamMember> listFreeTeamMember = registeredUserController.getListFreeTeamMember();
        if(listFreeTeamMember != null)
            return new ResponseEntity<>(listFreeTeamMember, HttpStatus.OK);
        return new ResponseEntity<>(listFreeTeamMember, HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per ottenere un TeamCoordinator estratto a caso dal DB
     *
     * @return TeamCoordinator casuale
     */
    @RequestMapping(path = "team_coordinator", method = RequestMethod.GET)
    public ResponseEntity<TeamCoordinator> getTeamCoordinator(){
        TeamCoordinator teamCoordinator = registeredUserController.getTeamCoordinator();
        if(teamCoordinator != null)
            return new ResponseEntity<>(teamCoordinator, HttpStatus.OK);
        return new ResponseEntity<>(teamCoordinator, HttpStatus.NOT_FOUND);

    }


    /**
     * Metodo che restituisce tutti gli InternalUser in base al loro ruolo.
     *
     * @param role ruolo del sistema da cercare.
     * @return lista di InternalUser
     */
    @RequestMapping(path= "getUserByRole/{role}", method = RequestMethod.GET)
    public ResponseEntity<List<? extends InternalUser>> getListByRole(@PathVariable SystemRole role)
    {
        List<? extends InternalUser> listInternalUser = registeredUserController.getListByRole(role);
        if(listInternalUser!= null)
            return new ResponseEntity<>(listInternalUser, HttpStatus.OK);
        return new ResponseEntity<>(listInternalUser, HttpStatus.NOT_FOUND);

    }

    /**
     * Metodo che restituisce tutti gli InternalUser impiegati in base al loro ruolo.
     *
     * @param role ruolo da cercare nel sistema
     * @return lista di InternalUser
     */
    @RequestMapping(path= "getEmployedUserByRole/{role}", method = RequestMethod.GET)
    public ResponseEntity<List<? extends InternalUser>> getEmployedUserByRole(@PathVariable SystemRole role)
    {
        List<? extends InternalUser> listInternalUser = registeredUserController.getEmployedUserByRole(role);
        if(listInternalUser!= null)
            return new ResponseEntity<>(listInternalUser, HttpStatus.OK);
        return new ResponseEntity<>(listInternalUser, HttpStatus.NOT_FOUND);

    }

    /**
     * Questa classe interna è usata per passare i parametri della chiamata Rest /login che "autentica" un Utente
     * che invia username e password.
     * Corrisponde ad un JSON del tipo :
     * {
     *     "username" : "alessio",
     *     "password" : "forzaroma"
     * }
     *
     */
    @SuppressWarnings("unused")
    public static class LoginBean {

        private String username;
        private String password;

        LoginBean(){}

        String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
