/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker;

import edu.eci.arsw.msgbroker.model.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author 2099340
 */
@RestController
@RequestMapping(value = "/puntos")
public class PuntosController {
    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    ManejadorPuntos mp;
    
    @RequestMapping(method = RequestMethod.POST)
    public  ResponseEntity<?> manejadorPostRecursoXX(@RequestBody Point pt) {
        try {
            msgt.convertAndSend("/topic/newpoint", pt);
            mp.adicionar(pt);
            if(mp.getSize()==4){
                msgt.convertAndSend("/topic/newpolygon",mp.Array());
                mp.reset();
            }
             return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(STOMPMessagesHandler.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.FORBIDDEN);
        }

    }
}
