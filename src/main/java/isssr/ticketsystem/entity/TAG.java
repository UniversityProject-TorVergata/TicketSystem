package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Il TAG è una parola chiave che un'utente può indicare all'apertura di un Ticket,
 * Usato per il filtraggio e la caratterizzazione dei Ticket.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TAG {
    Java,
    Python,
    C,
    PHP,
    Asp,
    Ruby,
    Javascript,
    Hibernate,
    Spring,
    Laravel,
    Jpa,
    Jdbc,
    Rest,
    API,
    Json,
    Query,
    Syntax ,
    Transaction,
    Deployment,
    GUI,
    Relations,
    Rollback,
    Dump,
    Injection,
    Performance,
    Server,
    Cost,
    Biling,
    Port,
    Connection,
    Settings,
    CORS,
    Export,
    Installation


}
