package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.CompanyController;
import isssr.ticketsystem.controller.TicketController;
import isssr.ticketsystem.entity.Company;
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
    private CompanyController companyController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Company> insertCompany(@RequestBody Company company) {
        Company createdCompany = companyController.insertCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        Company updatedCompany;
        try {
            updatedCompany =  companyController.updateCompany(id,company);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(company, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Company> findCompany(@PathVariable Long id) {
        Company companyFound = companyController.findCompanyById(id);
        return new ResponseEntity<>(companyFound, companyFound == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteCompany(@PathVariable Long id) {
        boolean deletedCompany = companyController.deleteCompany(id);
        return new ResponseEntity<>(deletedCompany, deletedCompany ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Company>> getCompanies() {
        List<Company> companies = companyController.getCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
}
