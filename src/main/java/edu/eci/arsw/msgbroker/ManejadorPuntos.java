/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker;

import edu.eci.arsw.msgbroker.model.Point;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2099340
 */

@Service
public class ManejadorPuntos {
    
    ConcurrentLinkedQueue<Point> puntos =    new ConcurrentLinkedQueue<Point>();

    
    
    public void adicionar ( Point pt){
        puntos.add(pt);
    }
    
    public int getSize(){
        return puntos.size();
    }
    
    public Object[] Array(){
        return puntos.toArray();
    }
    
    public void reset(){
        puntos = new ConcurrentLinkedQueue<Point>();
    }
    
    
}
