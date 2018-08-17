/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listarMongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jair Parra
 */
public class VerificarDuplicado {
    private static Mongo mongo;
   private static DB db;
    private static DBCollection collection;

    public static void main(String[] args) throws UnknownHostException {
        try {
        List<String> list = new ArrayList();
//        mongo = new Mongo("192.168.75.7",27017);
        mongo = new Mongo("localhost",27017);
        db =  mongo.getDB("bd");
        collection = db.getCollection("documento_restitucion_anexo.files");
//        collection = db.getCollection("documento_restitucion_anexo.files");

        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {            
            list.add((String) cursor.next().get("filename"));                       
        }
//        Collections.sort(list);
        Collections.reverse(list);
        int numerodocumento; 
        for (String filename : list) {
            DBObject query = new BasicDBObject("filename",filename);            
            numerodocumento = collection.find(query).count();            
            if (numerodocumento>1) {
                System.out.println(filename);
                DBObject paraborrar = collection.findAndRemove(query);
//                System.out.println(numerodocumento);
            }
        }
        mongo.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}
