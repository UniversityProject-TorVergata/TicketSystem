package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.PersonaDao;
import isssr.ticketsystem.entity.Persona;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class PersonaController {

    @Autowired
    private PersonaDao personaDao;

    @Transactional
    public @NotNull Persona insertPersona(@NotNull Persona persona) {

        Persona createdPersona = personaDao.save(persona);
        return createdPersona;
    }

    @Transactional
    public @NotNull Persona updatePersona(@NotNull Long id, @NotNull Persona updatedData) throws NotFoundEntityException {

        Persona toBeUpdatedPersona = personaDao.getOne(id);

        if (toBeUpdatedPersona == null)
            throw new NotFoundEntityException();

        toBeUpdatedPersona.update(updatedData);
        Persona updatedPersona = personaDao.save(toBeUpdatedPersona);

        return updatedPersona;
    }

    public Persona findPersonaById(@NotNull Long id) {
        Persona foundPersona = personaDao.getOne(id);
        return foundPersona;
    }

    public boolean deletePersona(@NotNull Long id) {
        if (!personaDao.existsById(id)) {
            return false;
        }
        personaDao.deleteById(id);
        return true;
    }

    public List<Persona> getPersonas() {

        return personaDao.findAll();
    }
}
