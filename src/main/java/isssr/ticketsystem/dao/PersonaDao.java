package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDao extends JpaRepository<Persona,Long> {
}
