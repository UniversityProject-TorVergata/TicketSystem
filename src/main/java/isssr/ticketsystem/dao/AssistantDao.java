package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantDao extends JpaRepository<Assistant, Long> {
}
