package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController e @Controller identificano uno Spring Bean che nell'architettura MVC è l'anello di congiunzione tra
// la View e il Controller (vedere l'annotazione @Service della classe RegisteredUserController).
// La differenzatra @Controller e @RestController è che @RestController (che estende @Controller) preconfigura tutti i
// metodi per accettare in input e restituire in output delle richieste HTTP il cui payload è in formato JSON.
// Per ottenere lo stesso comportamento del @RestController, si possono utilizzare l'annotazione @Controller e
// l'annotazione @ResponseBody; quest'ultima serve appunto a denotare che un metodo (o tutti i metodi di una classe)
// restituiscono dati in formati JSON. Gli attributi "produces" e "consumes" di @RequestMapping permettono di definire
// il MimeType dei dati restituiti e ricevuti, rispettivamente. Quando input e output sono in formato JSON, l'annotazione
// @RestController è un metodo sintetico per dichiararlo e fornire a Spring la configurazione necessaria per serialzizare
// e deserializzare il JSON.
@RestController
@RequestMapping(path = "company")
public class CompanyRestService {

    @Autowired
    private RegisteredUserController registeredUserController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RegisteredUser> insertRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        RegisteredUser createdRegisteredUser = registeredUserController.insertRegisteredUser(registeredUser);
        return new ResponseEntity<>(createdRegisteredUser, HttpStatus.CREATED);
    }

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

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RegisteredUser> findRegisteredUser(@PathVariable Long id) {
        RegisteredUser registeredUserTrovata = registeredUserController.findRegisteredUserById(id);
        return new ResponseEntity<>(registeredUserTrovata, registeredUserTrovata == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteRegisteredUser(@PathVariable Long id) {
        boolean deletedRegisteredUser = registeredUserController.deleteRegisteredUser(id);
        return new ResponseEntity<>(deletedRegisteredUser, deletedRegisteredUser ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<RegisteredUser>> getRegisteredUsers() {
        List<RegisteredUser> registeredUsers = registeredUserController.getRegisteredUsers();
        return new ResponseEntity<>(registeredUsers, HttpStatus.OK);
    }
}
