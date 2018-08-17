/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.Tika;

/**
 *
 * @author juliangarcia
 */
public class MongoDBUtil {

    private static Mongo mongo;
    private static DB db;
    private static DBCollection collection;
    private static GridFS gfsPhoto;

    public static void main(String[] args) {
        try {
            conexionMongo();
            String newFileName = "476176";
            /*File imageFile = new File("/home/juliangarcia/Escritorio/Julian_Garcia/Unidad de Victimas/Consolidado_Imagenes/AB0000696992.tif");           
             // get image file from local drive
             GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
             // set a new filename for identify purpose
             gfsFile.setFilename(newFileName);
             // save the image file into mongoDB
             gfsFile.save();
             // print the result
             DBCursor cursor = gfsPhoto.getFileList();
             while (cursor.hasNext()) {
             System.out.println(cursor.next());
             }*/
            // get image file by it's filename
            GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
            // save it into a new image file            
            imageForOutput.writeTo("/home/jairparra/Escritorio/Logs");
            Tika tika = new Tika();
            File file = new File("/home/jairparra/Escritorio/Logs");
            String mediaType = tika.detect(file);
            // remove the image file from mongoDB
            //gfsPhoto.remove(gfsPhoto.findOne(newFileName));
            System.out.println("Done");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void conexionMongo() {
        try {
//            mongo = new Mongo("192.168.70.78", 27017);
            mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("Restitucion");
            collection = db.getCollection("documento_restitucion_anexo");
            gfsPhoto = new GridFS(db, "documento_restitucion_anexo");
            //collection = db.getCollection("documento_microzona_anexo");
            //gfsPhoto = new GridFS(db, "documento_microzona_anexo");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoDBUtil.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (MongoException ex) {
            Logger.getLogger(MongoDBUtil.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}