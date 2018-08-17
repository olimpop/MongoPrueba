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
public class ListarDocumentos {

    private static Mongo mongo;
    private static DB db;
    private static DBCollection collection;

    public static void main(String[] args) throws UnknownHostException {
        try {
            List<String> list = new ArrayList<>();
            mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("Restitucion");
            collection = db.getCollection("documento_restitucion_anexo.files");
            DBCursor cursor = collection.find();
            while (cursor.hasNext()) {
//                System.out.println("Filename:" +  cursor.next().get("filename"));
                list.add((String) cursor.next().get("filename"));
            }
            Collections.sort(list);
            for (String filename : list) {
                System.out.println(filename);
                DBObject query = new BasicDBObject("filename", filename);
                int numerodocumento = collection.find(query).count();
                System.out.println(numerodocumento);
                if (numerodocumento>1) {
                   DBObject paraborrar = collection.findAndRemove(query);                   
                }
            }
            mongo.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}