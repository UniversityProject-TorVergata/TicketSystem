package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.StateMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateMachineDao extends JpaRepository<StateMachine,Long> {


}
