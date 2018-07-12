package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserController {

    private final RegisteredUserDao registeredUserDao;

    @Autowired
    public RegisteredUserController(RegisteredUserDao registeredUserDao) {
        this.registeredUserDao = registeredUserDao;
    }


    @Transactional
    public @NotNull RegisteredUser insertRegisteredUser(@NotNull RegisteredUser registeredUser) {

        return registeredUserDao.save(registeredUser);

    }

    @Transactional
    public @NotNull RegisteredUser updateRegisteredUser(@NotNull Long id, @NotNull RegisteredUser updatedData) {

        Optional<RegisteredUser> foundRegisteredUser = registeredUserDao.findById(id);
        RegisteredUser toBeUpdatedRegisteredUser;
        if(foundRegisteredUser.isPresent()) {
            toBeUpdatedRegisteredUser = foundRegisteredUser.get();
            toBeUpdatedRegisteredUser.update(updatedData);
            return registeredUserDao.save(toBeUpdatedRegisteredUser);
        }
        return null;
    }

    RegisteredUser findRegisteredUserById(@NotNull Long id) {

        Optional<RegisteredUser> foundRegisteredUser = registeredUserDao.findById(id);
        return foundRegisteredUser.orElse(null);

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



    private boolean deleteTeamCoordinator(TeamCoordinator teamCoordinator){
        List<TeamCoordinator> teamCoordinators = registeredUserDao.getTeamCoordinators();
        if(teamCoordinators.size()<2){
            return false;
        }
        registeredUserDao.deleteById(teamCoordinator.getId());
        return true;
    }

    private boolean deleteAdmin(Admin admin){
        List<Admin> admins = registeredUserDao.getAdmins();
        if(admins.size()<2){
            return false;
        }
        registeredUserDao.deleteById(admin.getId());
        return true;

    }

    public RegisteredUser getRegisteredUserByLogin(String username,String password){

        return registeredUserDao.getUserByLogin(username,password);

    }

    private List<TeamLeader> getListTeamLeader() {

       return registeredUserDao.getListTeamLeader();
    }

    public List<TeamMember> getListFreeTeamMember()
    {
        List<TeamMember> listFreeTeamMember = registeredUserDao.getListFreeTeamMember();
        List<TeamMember> outputList = new ArrayList<>(listFreeTeamMember);
        for(TeamMember tm : listFreeTeamMember){
            if(tm.getClass().equals(TeamLeader.class)){
                outputList.remove(tm);
            }
        }
        return outputList;
    }

    public TeamCoordinator getTeamCoordinator()
    {
        List<TeamCoordinator> teamCoordinators = registeredUserDao.getTeamCoordinators();
        if(teamCoordinators.size()==1)
            return teamCoordinators.get(0);
        int selectedTeamCoordinator = (int)(Math.random()*teamCoordinators.size());
        return teamCoordinators.get(selectedTeamCoordinator);
    }

   TeamMember getRandomTeamMember(){

        List<TeamMember> teamMembers = registeredUserDao.getTeamMembers();
        if(teamMembers.size()==1) {

            return teamMembers.get(0);
        }
        int selectedTeamMember = (int)(Math.random()*teamMembers.size());

        return teamMembers.get(selectedTeamMember);
    }

    TeamLeader getRandomTeamLeader(){
        List<TeamLeader> teamLeaders = registeredUserDao.getListTeamLeader();
        if(teamLeaders.size()==1)
            return teamLeaders.get(0);
        int selectedTeamLeader = (int)(Math.random()*teamLeaders.size());
        return teamLeaders.get(selectedTeamLeader);
    }


    public List<TeamLeader> getListEmployedTeamLeader() {
        return registeredUserDao.getListEmployedTeamLeader();

    }

    public List<TeamLeader> getListFreeEmployedTeamLeader() {
        return registeredUserDao.getListFreeTeamLeader();

    }

    private List<TeamCoordinator> getListTeamCoordinator()
    {
        return registeredUserDao.getTeamCoordinators();
    }

    private List<TeamMember> getListTeamMember()
    {
        return registeredUserDao.getAllTeamMember();

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

    public List<? extends InternalUser> getEmployedUserByRole(SystemRole role) {
        switch (role)
        {
            case TeamLeader:
                return getListEmployedTeamLeader();
            case TeamMember:
                return getListEmployedTeamMember();
            case TeamCoordinator:
                return getListTeamCoordinator();
        }
        return null;
    }

    private List<? extends InternalUser> getListEmployedTeamMember() {
        return registeredUserDao.getListEmployedTeamMember();

    }
}
