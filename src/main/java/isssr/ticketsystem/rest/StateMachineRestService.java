package isssr.ticketsystem.rest;

import isssr.ticketsystem.controller.StateMachineController;
import isssr.ticketsystem.entity.StateMachine;
import isssr.ticketsystem.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="state_machine")
@SuppressWarnings("ConstantConditions")
public class StateMachineRestService {

    private final StateMachineController stateMachineController;

    @Autowired
    public StateMachineRestService(StateMachineController stateMachineController) {
        this.stateMachineController = stateMachineController;
    }

    /**
     * Metodo che restituisce tutti i nome della varie FSM.
     *
     * @return Collection con i nomi delle FSM
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<Collection<StateMachine>> getStateMachines(){
        Collection<StateMachine> stateMachines = stateMachineController.getStateMachines();
        if(stateMachines!=null)
            return new ResponseEntity<>(stateMachines,HttpStatus.OK);
        return new ResponseEntity<>(stateMachines,HttpStatus.NOT_FOUND);
    }

    /**
     * Metodo per inserire una nuova FSM nel DB. Nel corpo della FSM bisogna inserire:
     * - il nome della stessa
     * - una stringa in base 64 che rappresenta il file XML della FSM
     * @param stateMachine la state machine da salvare
     * @return FSM creata
     */
    @RequestMapping(value="",method = RequestMethod.POST )
    public ResponseEntity<FSMResponse> insertStateMachine(@RequestBody StateMachine stateMachine){
        String result = stateMachineController.saveStateMachine(stateMachine);
        FSMResponse response = new FSMResponse(result);
        if(result == null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Servizio Rest usato per il download di un template di una macchina a stati.
     *
     * @return Template con stringa in base64 dell'xml della FSM di default.
     */
    @RequestMapping(value = "/downloadTemplate",method = RequestMethod.GET)
    public ResponseEntity<Template> getStateMachineTemplate(){
        String encode64xml = FileManager.encodeFile("./src/main/resources/state_machine/xml_files/StateMachine_01.xml");
        System.err.println(encode64xml);
        Template tmp = new Template(encode64xml);
        if(encode64xml!=null)
            return new ResponseEntity<>(tmp,HttpStatus.OK);
        return new ResponseEntity<>(tmp,HttpStatus.NOT_FOUND);
    }

    /**
     * Classe usata per inviare come risposta una stringa in base64 rappresentante una macchina a stati di default.
     *
     */
    @SuppressWarnings("unused")
    public static class Template {

        private String templateBase64;

        private Template(){

        }

        private Template(String templateBase64){
            this.templateBase64 = templateBase64;

        }



        public String getTemplateBase64() {
            return templateBase64;
        }

        public void setTemplateBase64(String templateBase64) {
            this.templateBase64 = templateBase64;
        }
    }


    /**
     * Classe usata per restituire il messaggio di errore o successo a seguito dell'inserimento di una macchina a stati.
     *
     */
    @SuppressWarnings("unused")
    public static class FSMResponse {
        private String response;

        public FSMResponse() {

        }

        FSMResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }


}
