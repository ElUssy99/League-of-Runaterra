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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Connector.Connector;
import Objects.Mazos;
import Objects.Usuarios;

public class LeagueOfRunaterra {
	
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
			//System.out.println(document);
			System.out.println(document.get("id") + " - " + document.get("nombre") + " - " + document.get("contra") + " - " + document.get("listaCartas") + " - " + document.get("listaMazos"));
		}
		
		System.out.println("\n--Login--");
		System.out.print("Usuario: ");
		String name = entrada.next();
		System.out.print("Contraseña: ");
		String pass = entrada.next();
		
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		
		for (Document document : docs) {
			if (name.equalsIgnoreCase(document.getString("nombre"))) {
				if (pass.equalsIgnoreCase(document.getString("contra"))) {
					juego(database, document);
				}
			}
		}
	
	}
	
	public static void juego(MongoDatabase database, Document usuario) {
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
					mazos(database, usuario);
					break;
				case 2:
					tienda(database, usuario);
					break;
				case 3:
					continuar = false;
					break;
				default:
					break;
				}
		}
	}
	
	public static void mazos(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||        Mazos        ||");
			System.out.println("||=====================||");
			System.out.println("	1. Nuevo mazo");
			System.out.println("	2. Editar mazo");
			System.out.println("	3. Borrar mazo");
			System.out.println("	4. Cargar mazos predefinidos");
			System.out.println("	5. <-- Atras");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
				case 1:
					nuevoMazo(database, usuario);
					break;
				case 2:
					
					break;
				case 3:
					continuar = false;
					break;
				case 4:
					
					break;
				case 5:
					continuar = false;
					juego(database, usuario);
					break;
				default:
					break;
				}
		}
	}
	
	public static void nuevoMazo(MongoDatabase database, Document usuario) {
		// Listar las cartas que tiene el usuario sin hacer una consulta a la Base de Datos
		Scanner entrada = new Scanner(System.in);
		
		ArrayList<Mazos> mazo = new ArrayList<Mazos>();
		
		System.out.println("||=====================||");
		System.out.println("|| LEAGUE OF RUNATERRA ||");
		System.out.println("||     Nuevo  Mazo     ||");
		System.out.println("||=====================||");
		
		System.out.print("\nNombre del Mazo: ");
		String nombre = entrada.nextLine();
		System.out.println("Lista de Cartas (-1 para salir):");
		
		boolean continuar = true;
		
		while(continuar) {
			System.out.print("  Introduce un ID de Carta: ");
			int id = entrada.nextInt();
			
			if (id == -1) {
				break;
			} else {
				MongoCollection<Document> collection = database.getCollection("Usuarios");

				Document find = new Document("id", usuario.get("id")); 
				MongoCursor<Document> resutl = collection.find(find).iterator();
				
				if (resutl.hasNext()) {
					Document d = resutl.next();
					Usuarios u = new Usuarios();
					
					ArrayList<Integer> listaCartas = new ArrayList<Integer>();
					ArrayList<Integer> listaMazos = new ArrayList<Integer>();
					
					u.setId(d.getInteger("id"));
					u.setNombre(d.getString("nombre"));
					u.setContra(d.getString("contra"));
					listaCartas = (ArrayList<Integer>) d.get("listaCartas");
					u.setListaCartas(listaCartas);
					listaMazos = (ArrayList<Integer>) d.get("listaMazos");
					u.setListaMazos(listaMazos);
					
					System.out.println("  " + u.toString());
					
					for (Integer idCarta : listaCartas) {
						if (id == idCarta) {
							
						}
					}
				} else {
					System.err.println("No existe esta carta. Prueba con otra.");
				}
			}
			
		}
	}
	
	public static void tienda(MongoDatabase database, Document usuario) {
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
					continuar = false;
					juego(database, usuario);
					break;
				default:
					break;
				}
		}
	}
	
}








