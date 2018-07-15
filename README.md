# README #

Questo progetto è un Back-End Server per un sistema di Ticketing

### Funzionalità implementate ###

* Architettura base del sistema 
* CRUD Entità del sistema
* Dispatching manuale del Ticket tra i ruoli del sistema
* Gestione del Ticket Workflow (ciclo di vita del Ticket) tramite macchina a stati configurata da file .xml


### Configurazione ###

* Creare un database PostgresSql e indicarne l'url nell'impostazione "spring.datasource.url"
nel file properties in "src/main/resources/application.properties"
* Nello stesso file indicare username e password del server postgres utilizzato
* Navigando nel menù File-->Project Structure-->Module aggiungere la dipendenza alla libreria FSMlibraryJar.jar presente in
"src/main/resources/lib"
* Questo Back-End è pensato per essere utilizzato in combinazione con  un server di Front-End appositamente creato e integrato
* E' possibile interagire con le funzionalità del server inviando i dati in formato JSON usando applicazioni rest come Postman , e Advanced Rest Service
utilizzando l'url http://localhost:8200/ticketsystem e aggiungendo i path specificati dai servizi rest definiti in 
"src/main/java/isssr/ticketsystem/rest"

### Repository GitHub ###

* [Back-End (questo)](https://github.com/UniversityProject-TorVergata/TicketSystem.git)
* [Front-End](https://github.com/UniversityProject-TorVergata/Ticketsystem-FE.git)

### Sviluppato nell'ambito del corso di Ingegneria dei Sistemi Software e dei Servizi di Rete (ISSSR) da ###

* Simone Minasola
* Alessio Di Luzio
* Alessio Nazio
* Angela De Lorenzo
* Emiliano Fiorentini
* Stefano Iafrate
* Andrea Pera

### Con il contributo e le indicazioni dei docenti ###

* Giovanni Cantone
* Manuel Mastrofini
* Simone Corrieri 