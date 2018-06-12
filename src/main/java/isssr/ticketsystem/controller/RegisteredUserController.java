package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.dao.TeamDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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

        Optional<RegisteredUser> foundRegisteredUser = registeredUserDao.findById(id);
        RegisteredUser toBeUpdatedRegisteredUser = foundRegisteredUser.get();

        if (toBeUpdatedRegisteredUser == null)
            throw new NotFoundEntityException();

        toBeUpdatedRegisteredUser.update(updatedData);
        RegisteredUser updatedRegisteredUser = registeredUserDao.save(toBeUpdatedRegisteredUser);

        return updatedRegisteredUser;
    }

    public RegisteredUser findRegisteredUserById(@NotNull Long id) {
        // RegisteredUser foundRegisteredUser = registeredUserDao.getOne(id);
        Optional<RegisteredUser> foundRegisteredUser = registeredUserDao.findById(id);
        RegisteredUser resultRegisteredUser = foundRegisteredUser.get();
        return resultRegisteredUser;
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

    public RegisteredUser getRegisteredUserByLogin(String username,String password){

        RegisteredUser foundRegisteredUser = registeredUserDao.getUserByLogin(username,password);
        return foundRegisteredUser;

    }

    public List<TeamLeader> getListTeamLeader() {

        List<TeamLeader> foundListTeamLeader = registeredUserDao.getListTeamLeader();
        return foundListTeamLeader;
    }

    public List<TeamMember> getListFreeTeamMember()
    {
        List<TeamMember> listFreeTeamMember = registeredUserDao.getListFreeTeamMember();
        return listFreeTeamMember;
    }

    public TeamCoordinator getTeamCoordinator()
    {
        TeamCoordinator teamCoordinator = registeredUserDao.getTeamCoordinator();
        return teamCoordinator;
    }


}
