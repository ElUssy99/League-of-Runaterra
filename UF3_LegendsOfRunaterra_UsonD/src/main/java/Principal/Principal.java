package Principal;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Connector.Connector;
import Objects.Usuarios;

public class Principal {
	
	public static void main(String[] args) {
		menuPrincipal(connect());
	}
	
	public static MongoDatabase connect() {
		MongoClient mongoCli = null;
		try {
			mongoCli = Connector.crearConexion();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MongoDatabase database = mongoCli.getDatabase("LeagueOfRunaterra");
		
//		MongoCollection<Document> collection = database.getCollection("Cartas");
//		FindIterable<Document> docs = collection.find();
//
//		for (Document document : docs) {
//			System.out.println(document);
//		}
		
		return database;
	}
	
	private static void disableLogs() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		mongoLogger.getLogger("org.mongodb.driver.connection").setLevel(Level.OFF);
		mongoLogger.getLogger("org.mongodb.driver.management").setLevel(Level.OFF);
		mongoLogger.getLogger("org.mongodb.driver.cluster").setLevel(Level.OFF);
		mongoLogger.getLogger("org.mongodb.driver.protocol.insert").setLevel(Level.OFF);
		mongoLogger.getLogger("org.mongodb.driver.protocol.query").setLevel(Level.OFF);
		mongoLogger.getLogger("org.mongodb.driver.protocol.update").setLevel(Level.OFF);
	}
	
	public static void menuPrincipal(MongoDatabase database) {
		
//		try {
//			database.wait(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		disableLogs();
		
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		System.out.println("||||=====================||||");
		System.out.println("||||=====================||||");
		System.out.println("|||| LEAGUE OF RUNATERRA ||||");
		System.out.println("||||=====================||||");
		System.out.println("||||=====================||||");
		
		while (continuar) {
			System.out.println("\n||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||        Login        ||");
			System.out.println("||=====================||");
			System.out.println("	1. Login");
			System.out.println("	2. Salir");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				menuLogin(database);
				break;
			case 2:
				continuar = false;
				break;
			default:
				break;
			}
		}	
	}
	
	public static void menuLogin(MongoDatabase database) {
		Scanner entrada = new Scanner(System.in);
		
		MongoCollection<Document> collection = database.getCollection("Usuarios");
		FindIterable<Document> docs = collection.find();

		for (Document document : docs) {
			System.out.println(document);
			System.out.println(document.get("nombre"));
		}
		
		System.out.println("\n--Login--");
		System.out.print("Usuario: ");
		String name = entrada.next();
		System.out.print("Contraseña: ");
		String pass = entrada.next();
		
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		
		for (Document document : docs) {
			if (name == document.get("name")) {
				if (pass == document.get("contra")) {
					System.out.println("hola");
					juego(database);
				}
			}
		}
	
	}
	
	public static void juego(MongoDatabase database) {
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("\n||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||   Opciones  juego   ||");
			System.out.println("||=====================||");
			System.out.println("	1. Mazos");
			System.out.println("	2. Tienda");
			System.out.println("	3. Log out");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				mazos(database);
				break;
			case 2:
				tienda(database);
				break;
			case 3:
				continuar = false;
				break;
			default:
				break;
			}
		}
	}
	
	public static void mazos(MongoDatabase database) {
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||        Mazos        ||");
			System.out.println("||=====================||");
			System.out.println("	1. Nueva mazo");
			System.out.println("	2. Editar mazo");
			System.out.println("	3. Borrar mazo");
			System.out.println("	4. Cargar mazos predefinidos");
			System.out.println("	4. <-- Atras");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				continuar = false;
				break;
			default:
				break;
			}
		}
	}
	
	public static void tienda(MongoDatabase database) {
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||        Tienda       ||");
			System.out.println("||=====================||");
			System.out.println("	1. Comprar");
			System.out.println("	2. <-- Atras");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				continuar = false;
				break;
			default:
				break;
			}
		}
	}
	
}








