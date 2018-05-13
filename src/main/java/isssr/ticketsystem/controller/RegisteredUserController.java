package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class RegisteredUserController {

    @Autowired
    private RegisteredUserDao registeredUserDao;

    @Transactional
    public @NotNull RegisteredUser insertRegisteredUser(@NotNull RegisteredUser registeredUser) {

        registeredUser.setCreated_at(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()));
        RegisteredUser createdRegisteredUser = registeredUserDao.save(registeredUser);
        return createdRegisteredUser;
    }

    @Transactional
    public @NotNull RegisteredUser updateRegisteredUser(@NotNull Long id, @NotNull RegisteredUser updatedData) throws NotFoundEntityException {

        RegisteredUser toBeUpdatedRegisteredUser = registeredUserDao.getOne(id);

        if (toBeUpdatedRegisteredUser == null)
            throw new NotFoundEntityException();

        toBeUpdatedRegisteredUser.updateRegisteredUser(updatedData);
        RegisteredUser updatedRegisteredUser = registeredUserDao.save(toBeUpdatedRegisteredUser);

        return updatedRegisteredUser;
    }

    public RegisteredUser findRegisteredUserById(@NotNull Long id) {
        RegisteredUser foundRegisteredUser = registeredUserDao.getOne(id);
        return foundRegisteredUser;
    }

    public boolean deleteRegisteredUser(@NotNull Long id) {
        if (!registeredUserDao.existsById(id)) {
            return false;
        }
        registeredUserDao.deleteById(id);
        return true;
    }

    public List<RegisteredUser> getRegisteredUsers() {

        return registeredUserDao.findAll();
    }
}
