package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Persona;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaDao extends JpaRepository<Persona, Long> {
}
