package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantDao extends JpaRepository<TeamMember, Long> {
}
