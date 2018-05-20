package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.RegisteredUserController;
import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.exception.NotFoundEntityException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "registered_user")
public class RegisteredUserRestService {

    @Autowired
    private RegisteredUserController registeredUserController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RegisteredUser> insertRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        RegisteredUser createdRegisteredUser = registeredUserController.insertRegisteredUser(registeredUser);
        return new ResponseEntity<>(createdRegisteredUser, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<RegisteredUser> updateRegisteredUser(@PathVariable Long id, @RequestBody RegisteredUser registeredUser) {
        RegisteredUser updatedRegisteredUser;
        try {
            updatedRegisteredUser = registeredUserController.updateRegisteredUser(id, registeredUser);
        } catch (NotFoundEntityException e) {
            return new ResponseEntity<>(registeredUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRegisteredUser, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RegisteredUser> findRegisteredUser(@PathVariable Long id) {
        RegisteredUser registeredUserTrovata = registeredUserController.findRegisteredUserById(id);
        return new ResponseEntity<>(registeredUserTrovata, registeredUserTrovata == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteRegisteredUser(@PathVariable Long id) {
        boolean deletedRegisteredUser = registeredUserController.deleteRegisteredUser(id);
        return new ResponseEntity<>(deletedRegisteredUser, deletedRegisteredUser ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<RegisteredUser>> getRegisteredUsers() {
        List<RegisteredUser> registeredUsers = registeredUserController.getRegisteredUsers();
        return new ResponseEntity<>(registeredUsers, HttpStatus.OK);
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RegisteredUser> getRegisteredUserByLogin(@RequestBody LoginBean loginBean){
        RegisteredUser userLogged = registeredUserController.getRegisteredUserByLogin(loginBean.getUsername(),loginBean.getPassword());
        if(userLogged != null)
            return new ResponseEntity<>(userLogged,HttpStatus.OK);
        else
            return new ResponseEntity<>(userLogged,HttpStatus.NOT_FOUND);

    }


    public static class LoginBean {

        private String username;
        private String password;

        public LoginBean(){};

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
