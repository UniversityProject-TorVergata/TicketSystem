package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.PersonaDao;
import isssr.ticketsystem.entity.Persona;
import isssr.ticketsystem.exception.EntitaNonTrovataException;
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
    public @NotNull Persona creaPersona(@NotNull Persona persona) {

        Persona personaSalvata = personaDao.save(persona);
        return personaSalvata;
    }

    @Transactional
    public @NotNull Persona aggiornaPersona(@NotNull Long id, @NotNull Persona datiAggiornati) throws EntitaNonTrovataException {

        Persona personaDaAggiornare = personaDao.getOne(id);

        if (personaDaAggiornare == null)
            throw new EntitaNonTrovataException();

        personaDaAggiornare.aggiorna(datiAggiornati);
        Persona personaAggiornata = personaDao.save(personaDaAggiornare);

        return personaAggiornata;
    }

    public Persona cercaPersonaPerId(@NotNull Long id) {

        Persona personaTrovata = personaDao.getOne(id);

        return personaTrovata;
    }

    public boolean eliminaPersona(@NotNull Long id) {

        if (!personaDao.existsById(id)) {
            return false;
        }

        personaDao.deleteById(id);

        return true;
    }

    public List<Persona> prelevaPersone() {

        return personaDao.findAll();
    }
}
