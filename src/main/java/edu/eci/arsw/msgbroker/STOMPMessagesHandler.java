/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker;

import edu.eci.arsw.msgbroker.model.Point;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 2099340
 */
@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    ManejadorPuntos mp;
    

    @MessageMapping("/newpoint")    
    public synchronized void getLine(Point pt) throws Exception {
        msgt.convertAndSend("/topic/newpoint", pt);
        mp.adicionar(pt);
        if(mp.getSize()==4){
            msgt.convertAndSend("/topic/newpolygon",mp.Array());
            mp.reset();
        }
    }
}
