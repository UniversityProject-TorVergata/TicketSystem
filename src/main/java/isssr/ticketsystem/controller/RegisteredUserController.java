package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.RegisteredUserDao;
import isssr.ticketsystem.entity.*;
import isssr.ticketsystem.enumeration.SystemRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserController {

    @Autowired
    private RegisteredUserDao registeredUserDao;


    @Transactional
    public @NotNull RegisteredUser insertRegisteredUser(@NotNull RegisteredUser registeredUser) {

        RegisteredUser createdRegisteredUser = registeredUserDao.save(registeredUser);
        return createdRegisteredUser;
    }

    @Transactional
    public @NotNull RegisteredUser updateRegisteredUser(@NotNull Long id, @NotNull RegisteredUser updatedData) {

        Optional<RegisteredUser> foundRegisteredUser = registeredUserDao.findById(id);
        RegisteredUser toBeUpdatedRegisteredUser = foundRegisteredUser.get();
        toBeUpdatedRegisteredUser.update(updatedData);
        RegisteredUser updatedRegisteredUser = registeredUserDao.save(toBeUpdatedRegisteredUser);
        return updatedRegisteredUser;
    }

    public RegisteredUser findRegisteredUserById(@NotNull Long id) {

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

    public TeamMember getRandomTeamMember(){
        Log logger = LogFactory.getLog(getClass());
        List<TeamMember> teamMembers = registeredUserDao.getTeamMembers();
        if(teamMembers.size()==1) {
            logger.error("Solo un teamMember");
            return teamMembers.get(0);
        }
        int selectedTeamMember = (int)(Math.random()*teamMembers.size());
        logger.error("Estratto teamMember : " + selectedTeamMember);
        return teamMembers.get(selectedTeamMember);
    }

    public TeamLeader getRandomTeamLeader(){
        List<TeamLeader> teamLeaders = registeredUserDao.getListTeamLeader();
        if(teamLeaders.size()==1)
            return teamLeaders.get(0);
        int selectedTeamLeader = (int)(Math.random()*teamLeaders.size());
        return teamLeaders.get(selectedTeamLeader);
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
        List<TeamMember> foundListTeamMember = registeredUserDao.getListEmployedTeamMember();
        return foundListTeamMember;
    }
}
