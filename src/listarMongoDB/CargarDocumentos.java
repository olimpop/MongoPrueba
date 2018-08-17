/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listarMongoDB;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author Jair Parra
 */
public class CargarDocumentos {

    private static Mongo mongo;
    private static DB db;

    public static void main(String[] args) throws UnknownHostException, IOException {
        try {            
            mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("db");
            for (int i = 2150000; i < 3150000; i++) {
                String dbFileName = Integer.toString(i);                      
                File imageFile = new File("D:\\prueba\\test.pdf");
                GridFS gfsPhoto = new GridFS(db, "documento_restitucion_anexo");
                GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
                gfsFile.setFilename(dbFileName);
                gfsFile.save();
            }
            mongo.close();
        } catch (UnknownHostException ex) {
        }
    }
}
