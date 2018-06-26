package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Target;
import isssr.ticketsystem.enumeration.TargetState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface TargetDao extends JpaRepository<Target, Long> {

    @Query("select t from Target t where t.targetState = :state")
    List<Target> getActiveTarget(@Param("state") TargetState targetState);
}
