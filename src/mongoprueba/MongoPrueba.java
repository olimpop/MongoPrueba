/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongoprueba;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import conexion.Conexion;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jair Parra
 */
public class MongoPrueba {

    /**
     * @param args the command line arguments
     */
    private static Mongo mongo;
    private static DB db;
    private static DBCollection collection;
    private static GridFS gfsPhoto;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
              // TODO code application logic here
       String sql = "SELECT id_documento FROM public.documento_restitucion;\n";       
        Conexion cnx = new Conexion();
        cnx.Conectar();
        conexionMongo();
      try {
          
          ResultSet rs;
            Statement stmt = cnx.getConexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
          if (!rs.next()) {
                System.out.println("No hay registros");
            } else {
                rs.first();                
                do {                    
                    System.out.println("hice este documento: "+rs.getInt("id_documento"));
                    String FileName = "" + rs.getString("id_documento");
//                    GridFSDBFile iO = gfsPhoto.findOne(FileName);
//                    try {
//                        iO.validate();
//                        System.out.println(iO.getMD5().toString());
//                    } catch (Exception e) {                    
//                    iO = null; 
//                    }                    
                    
                    BasicDBObject query = new BasicDBObject();
                    query.put("filename", rs.getInt("id_documento"));
                    
                    mongo.close();
                    
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        } finally {
            cnx.CloseConection();
        }
    }
    
    private static void conexionMongo() {
        try {
            mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("Restitucion");
            collection = db.getCollection("documento_restitucion_anexo");
            gfsPhoto = new GridFS(db, "documento_restitucion_anexo");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoPrueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (MongoException ex) {
            Logger.getLogger(MongoPrueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }    
    }
}