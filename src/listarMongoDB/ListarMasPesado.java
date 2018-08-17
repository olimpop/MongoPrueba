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
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jair Parra
 */
public class ListarMasPesado {

    private static Mongo mongo;
    private static DB db;
    private static DBCollection collection;

    static class Persona implements Comparable<Persona> {

        public String filename;
        public float tamano;

        public Persona(String filename, float tamano) {
            this.filename = filename;
            this.tamano = tamano;
        }
        @Override
        public int compareTo(Persona o) {
            if (tamano < o.tamano) {return 1;}
            if (tamano > o.tamano) { return -1;}
            return 0;
        }
    }
    static void imprimeArrayPersonas(Persona[] array) {
//      for (int i = 0; i < array.length; i++) {
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + ". Filename: " + array[i].filename + " - Tamaño: " + array[i].tamano);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        try {
            Integer i = 0;
            List<String> list = new ArrayList<>();
            String col = "documento_restitucion_anexo";
            mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("Restitucion");            
            collection = db.getCollection(col+".files");
            DBCursor cursor = collection.find();
            Persona[] arrayPersonas = new Persona[collection.find().count()];
            while (cursor.hasNext()) {
                list.add((String) cursor.next().get("filename"));
            }
            for (String filename : list) {
                DBObject query = new BasicDBObject("filename", filename);
                GridFS gridFs = new GridFS(db, col);
                GridFSDBFile outputImageFile = gridFs.findOne(query);
//                System.out.println("filename: " +filename +" Total Chunks: " + outputImageFile.numChunks());
//                System.out.println("filename: " +filename +" Total Chunks: " + outputImageFile.getChunkSize());
//                System.out.println("filename: " + filename + " Total Length: " + outputImageFile.getLength());
//                System.out.println("filename: " + filename + " Total md5: " + outputImageFile.getMD5());
//                System.out.println("filename: " + filename + " Total UploadDate: " + outputImageFile.getUploadDate()   );
                arrayPersonas[i] = new Persona(filename, outputImageFile.getLength()/(1024*1024*1024));
                i++;
            }
            mongo.close();
            Arrays.sort(arrayPersonas);            
            imprimeArrayPersonas(arrayPersonas);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}