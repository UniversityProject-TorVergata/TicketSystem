package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.CompanyController;
import isssr.ticketsystem.entity.Company;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
