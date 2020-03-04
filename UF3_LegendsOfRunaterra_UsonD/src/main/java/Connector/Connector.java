package Connector;

import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Connector {
	
	/**
     * Main del proyecto.
     * @param args
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Prueba conexión MongoDB");
        MongoClient mongo = crearConexion();
 
        if (mongo != null) {
            System.out.println("Lista de bases de datos: ");
            printDatabases(mongo);
 
        } else {
            System.out.println("Error: Conexión no establecida");
        }
    }
 
    /**
     * Clase para crear una conexión a MongoDB.
     * @return MongoClient conexión
     */
    public static MongoClient crearConexion() throws UnknownHostException {

    	MongoClientURI uri = new MongoClientURI(
    			"mongodb+srv://ElUssy99:hola123456@cluster0-377gj.mongodb.net/test?retryWrites=true&w=majority");

		MongoClient mongoClient = new MongoClient(uri);
		
//		MongoDatabase database = mongoClient.getDatabase("\r\n" + 
//				"LeagueOfRunaterra");
//		MongoCollection<Document> collection = database.getCollection("Cartas");
//		FindIterable<Document> docs = collection.find();
//
//		for (Document document : docs) {
//			System.out.println(document);
//		}
 
        return mongoClient;
    }
 
    /**
     * Clase que imprime por pantalla todas las bases de datos MongoDB.
     * @param mongo conexión a MongoDB
     */
    private static void printDatabases(MongoClient mongo) {
        MongoCursor<String> dbs = mongo.listDatabaseNames().iterator();
         while(dbs.hasNext()) {
             System.out.println(dbs.next());
         }
    }
	
}
