package isssr.ticketsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Persona {

    @Id
    @GeneratedValue // Autoincrement
    private Long id;

    private String nome;
    private String cognome;


    public Persona(String nome, String cognome) {

        this.nome = nome;
        this.cognome = cognome;
    }

    public void aggiorna(@NotNull Persona datiAggiornati) {

        this.nome = datiAggiornati.nome;
        this.cognome = datiAggiornati.cognome;
    }
}
