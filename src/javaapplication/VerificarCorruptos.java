/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.net.UnknownHostException;

public class VerificarCorruptos extends Thread {

    public static void main(String[] args) {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("db");
            DBCollection collection = db.getCollection("documento_restitucion_anexo.files");
            int registros = collection.find().count();
            System.out.println("Numero de Registros de la Coleccion: "+registros);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        Tarea buscar1 = new Tarea(1, 316000);
        Tarea buscar2 = new Tarea(316001, 632000);
        Tarea buscar3 = new Tarea(632001, 948000);
        Tarea buscar4 = new Tarea(948001, 1264000);
        Tarea buscar5 = new Tarea(1264001, 1580000);
        Tarea buscar6 = new Tarea(1580001, 1896000);
        Tarea buscar7 = new Tarea(1896001, 2212000);
        Tarea buscar8 = new Tarea(2212001, 2528000);
        Tarea buscar9 = new Tarea(2528001, 2844000);
        Tarea buscar10 = new Tarea(2844001, 3160000);

        buscar1.start();
        buscar2.start();
        buscar3.start();
        buscar4.start();
        buscar5.start();
        buscar6.start();
        buscar7.start();
        buscar8.start();
        buscar9.start();
        buscar10.start();
    }
}
