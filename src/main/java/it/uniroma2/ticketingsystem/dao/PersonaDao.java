package it.uniroma2.ticketingsystem.dao;

import it.uniroma2.ticketingsystem.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDao extends JpaRepository<Persona,Long> {
}
