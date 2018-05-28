package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.RegisteredUser;
import isssr.ticketsystem.entity.TeamLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RegisteredUserDao extends JpaRepository<RegisteredUser, Long> {

    @Query("select u from RegisteredUser u where u.username = :username and u.password = :password")
    RegisteredUser getUserByLogin(@Param("username") String username,@Param("password") String password);

    @Query("select tl from TeamLeader tl ")
    List<TeamLeader> getListTeamLeader();

}
