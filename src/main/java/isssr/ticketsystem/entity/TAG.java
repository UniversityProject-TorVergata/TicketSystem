package isssr.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Il TAG è una parola chiave che un'utente può indicare all'apertura di un Ticket,
 * Usato per il filtraggio e la caratterizzazione dei Ticket.
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TAG {
    java,
    python,
    C_plus_plus,
    C,
    C_sharp,
    PHP,
    Node_js,
    Asp,
    ruby,
    javascript,
    Hibernate,
    Spring,
    Laravel,
    jpa,
    jdbc,
    rest,
    API,
    Json,
    query,
    syntax ,
    transaction,
    deployment,
    GUI,
    relations,
    rollback,
    dump,
    injection,
    performance,
    server,
    cost,
    biling,
    port,
    connection,
    settings,
    CORS,
    export,
    _import,
    installation

}
