package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.dao.TeamDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.SystemRole;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
        RegisteredUser user = findRegisteredUserById(id);
        if(user.getClass().equals(TeamCoordinator.class)){
            return deleteTeamCoordinator((TeamCoordinator) user);
        }

        if(user.getClass().equals(Admin.class)){
            return deleteAdmin((Admin) user);
        }
        registeredUserDao.deleteById(id);
        return true;
    }



    public boolean deleteTeamCoordinator(TeamCoordinator teamCoordinator){
        List<TeamCoordinator> teamCoordinators = registeredUserDao.getTeamCoordinators();
        if(teamCoordinators.size()<2){
            return false;
        }
        registeredUserDao.deleteById(teamCoordinator.getId());
        return true;
    }

    public boolean deleteAdmin(Admin admin){
        List<Admin> admins = registeredUserDao.getAdmins();
        if(admins.size()<2){
            return false;
        }
        registeredUserDao.deleteById(admin.getId());
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
        List<TeamCoordinator> teamCoordinators = registeredUserDao.getTeamCoordinators();
        if(teamCoordinators.size()==1)
            return teamCoordinators.get(0);
        int selectedTeamCoordinator = (int)(Math.random()*teamCoordinators.size());
        return teamCoordinators.get(selectedTeamCoordinator);
    }


    public List<TeamLeader> getListEmployedTeamLeader() {
        List<TeamLeader> foundListTeamLeader = registeredUserDao.getListEmployedTeamLeader();
        return foundListTeamLeader;
    }

    public List<TeamLeader> getListFreeEmployedTeamLeader() {
        List<TeamLeader> foundListTeamLeader = registeredUserDao.getListFreeTeamLeader();
        return foundListTeamLeader;
    }

    public List<TeamCoordinator> getListTeamCoordinator()
    {
        List<TeamCoordinator> listTeamCoordinator = registeredUserDao.getTeamCoordinators();
        return listTeamCoordinator;
    }

    public List<TeamMember> getListTeamMember()
    {
        List<TeamMember> listTeamMember = registeredUserDao.getAllTeamMember();
        return listTeamMember;
    }

    public List<? extends InternalUser> getListByRole (SystemRole role)
    {
        switch (role)
        {
            case TeamLeader:
                return getListTeamLeader();
            case TeamMember:
                return getListTeamMember();
            case TeamCoordinator:
                return getListTeamCoordinator();
        }
        return null;
    }
}
