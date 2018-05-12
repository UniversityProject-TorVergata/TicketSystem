package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.PersonaController;
import isssr.ticketsystem.entity.Persona;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController e @Controller identificano uno Spring Bean che nell'architettura MVC è l'anello di congiunzione tra
// la View e il Controller (vedere l'annotazione @Service della classe PersonaController).
// La differenzatra @Controller e @RestController è che @RestController (che estende @Controller) preconfigura tutti i
// metodi per accettare in input e restituire in output delle richieste HTTP il cui payload è in formato JSON.
// Per ottenere lo stesso comportamento del @RestController, si possono utilizzare l'annotazione @Controller e
// l'annotazione @ResponseBody; quest'ultima serve appunto a denotare che un metodo (o tutti i metodi di una classe)
// restituiscono dati in formati JSON. Gli attributi "produces" e "consumes" di @RequestMapping permettono di definire
// il MimeType dei dati restituiti e ricevuti, rispettivamente. Quando input e output sono in formato JSON, l'annotazione
// @RestController è un metodo sintetico per dichiararlo e fornire a Spring la configurazione necessaria per serialzizare
// e deserializzare il JSON.
@RestController
@RequestMapping(path = "persona")
public class PersonaRestService {

    @Autowired
    private PersonaController personaController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Persona> insertPersona(@RequestBody Persona persona) {
        Persona createdPersona = personaController.insertPersona(persona);
        return new ResponseEntity<>(createdPersona, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        Persona updatedPersona;
        try {
            updatedPersona = personaController.updatePersona(id, persona);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(persona, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPersona, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Persona> findPersona(@PathVariable Long id) {
        Persona personaTrovata = personaController.findPersonaById(id);
        return new ResponseEntity<>(personaTrovata, personaTrovata == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deletePersona(@PathVariable Long id) {
        boolean deletedPersona = personaController.deletePersona(id);
        return new ResponseEntity<>(deletedPersona, deletedPersona ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Persona>> getPersonas() {
        List<Persona> personas = personaController.getPersonas();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }
}
