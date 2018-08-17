/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tarea extends Thread {

    private int indicebajo;
    private int indicealto;
    private static Mongo mongo189;
    private static DB db189;
    private static DBCollection collection189;
    private static GridFS gfsPhoto189;

    public Tarea(Integer indicebajo, Integer indicealto) {
        this.indicebajo = indicebajo;
        this.indicealto = indicealto;
    }

    @Override
    public void run() {
        System.out.println("Indice Bajo:" + this.indicebajo +" Indice Alto: "+ this.indicealto);
        try {
            mongo189 = new Mongo("localhost", 27017);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MongoException ex) {
            Logger.getLogger(Tarea.class.getName()).log(Level.SEVERE, null, ex);
        }
        db189 = mongo189.getDB("db");
        collection189 = db189.getCollection("documento_restitucion_anexo");
        gfsPhoto189 = new GridFS(db189, "documento_restitucion_anexo");
        for (int i = this.indicebajo; i <= this.indicealto; i++) {            
            GridFSDBFile iO189 = gfsPhoto189.findOne(Integer.toString(i));            
            try {
                iO189.validate();
            } catch (Exception e) {
                System.out.println("Documento Errrado" + i);
            }
        }
//        mongo189.close();
    }
}
