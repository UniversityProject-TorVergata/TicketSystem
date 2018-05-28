package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.TeamLeader;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Questa classe gestisce le richieste HTTP che giungono sul path specificato ("registered_user")
 * attraverso i metodi definiti nella classe RegisteredUserController.
 */

@RestController
@RequestMapping(path = "registered_user")
public class RegisteredUserRestService {

    @Autowired
    private RegisteredUserController registeredUserController;

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
        RegisteredUser updatedRegisteredUser;
        try {
            updatedRegisteredUser = registeredUserController.updateRegisteredUser(id, registeredUser);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(registeredUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRegisteredUser, HttpStatus.OK);
    }


    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restituite le info relative all'utente specificato.
     * @param id Id dell'utente da visualizzare.
     * @return utente da visualizzare + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.RegisteredUserController
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RegisteredUser> findRegisteredUser(@PathVariable Long id) {
        RegisteredUser registeredUserTrovata = registeredUserController.findRegisteredUserById(id);
        return new ResponseEntity<>(registeredUserTrovata, registeredUserTrovata == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
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
        return new ResponseEntity<>(deletedRegisteredUser, deletedRegisteredUser ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo usato per la gestione di una GET che arriva sull'url specificato. A fronte di
     * una richiesta di questo tipo vengono restuiti tutti gli utenti registrati
     * presenti nel DB.
     * @return Utenti registrati presenti nel DB + esito della richiesta HTTP.
     * @see isssr.ticketsystem.controller.RegisteredUserController
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<RegisteredUser>> getRegisteredUsers() {
        List<RegisteredUser> registeredUsers = registeredUserController.getRegisteredUsers();
        return new ResponseEntity<>(registeredUsers, HttpStatus.OK);
    }

    /**
     * Metodo usato per la gestione del login di un utente precedentemente registrato.
     * @param loginBean oggetto contenente username e password dell'utente che vuole loggarsi.
     * @return esito del login
     */
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RegisteredUser> getRegisteredUserByLogin(@RequestBody LoginBean loginBean){
        RegisteredUser userLogged = registeredUserController.getRegisteredUserByLogin(loginBean.getUsername(),loginBean.getPassword());
        if(userLogged != null)
            return new ResponseEntity<>(userLogged,HttpStatus.OK);
        else
            return new ResponseEntity<>(userLogged,HttpStatus.NOT_FOUND);

    }

    @RequestMapping(path = "/team_leader", method = RequestMethod.GET)
    public ResponseEntity<List<TeamLeader>> getListTeamLeader() {
        List<TeamLeader> listTeamLeader = registeredUserController.getListTeamLeader();
        if(listTeamLeader != null)
            return new ResponseEntity<>(listTeamLeader,HttpStatus.OK);
        else
            return new ResponseEntity<>(listTeamLeader,HttpStatus.NOT_FOUND);
    }

    public static class LoginBean {

        private String username;
        private String password;

        public LoginBean(){};

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
    }
}
