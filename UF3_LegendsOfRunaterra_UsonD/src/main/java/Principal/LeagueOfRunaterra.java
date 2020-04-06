package Principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.Synthesizer;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import Connector.Connector;
import Objects.Cartas;
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
			
			disableLogs();
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MongoDatabase database = mongoCli.getDatabase("LeagueOfRunaterra");
		
		cargarUsuarios(database);
		cargarCartas(database);
		cargarMazos(database);
		
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
	
	public static void cargarCartas(MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("Cartas");

		collection.drop();

		JSONParser parser = new JSONParser();
		File f = new File("..\\UF3_LegendsOfRunaterra_UsonD\\src\\main\\resources\\Cartas.json");

		try {
			FileReader fr = new FileReader(f);
			JSONArray array = (JSONArray) parser.parse(fr);
			Iterator<?> iterator = array.iterator();

			while (iterator.hasNext()) {
				JSONObject object = (JSONObject) iterator.next();
				Cartas carta = new Cartas();
				carta.setID(Integer.parseInt(object.get("id").toString()));
				carta.setTipo((String) object.get("tipo"));
				carta.setNombre((String) object.get("nombre_carta"));
				carta.setCoste(Integer.parseInt(object.get("coste_invocacion").toString()));
				carta.setAtaque(Integer.parseInt(object.get("ataque").toString()));
				carta.setVida(Integer.parseInt(object.get("vida").toString()));
				carta.setHabilidadEspecial((String) object.get("habilidad_especial"));
				carta.setFaccion((String) object.get("faccion"));

				Document doc = new Document("id", carta.getID()).append("tipo", carta.getTipo())
						.append("nombre_carta", carta.getNombre())
						.append("coste_invocacion", carta.getCoste()).append("ataque", carta.getAtaque())
						.append("vida", carta.getVida()).append("habilidad_especial", carta.getHabilidadEspecial())
						.append("faccion", carta.getFaccion());
				collection.insertOne(doc);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		System.out.println("¡Cartas cargadas correctamente en la coleccion!");
	}
	
	public static void cargarUsuarios(MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("Usuarios");

		collection.drop();

		JSONParser parser = new JSONParser();
		File f = new File("..\\UF3_LegendsOfRunaterra_UsonD\\src\\main\\resources\\Usuarios.json");

		try {
			FileReader fr = new FileReader(f);
			JSONArray array = (JSONArray) parser.parse(fr);
			Iterator<?> iterator = array.iterator();

			while (iterator.hasNext()) {
				JSONObject object = (JSONObject) iterator.next();
				Usuarios usuario = new Usuarios();
				usuario.setId(Integer.parseInt(object.get("id_usuario").toString()));
				usuario.setContra((String) object.get("contrasenya_usuario"));
				usuario.setNombre((String) object.get("nombre_usuario"));
				usuario.setListaMazos((ArrayList<Integer>) object.get("mazos_usuario"));
				usuario.setListaCartas((ArrayList<Integer>) object.get("cartas_usuario"));

				Document doc = new Document("id_usuario", usuario.getId())
						.append("contrasenya_usuario", usuario.getContra())
						.append("nombre_usuario", usuario.getNombre())
						.append("mazos_usuario", usuario.getListaMazos())
						.append("cartas_usuario", usuario.getListaCartas());
				collection.insertOne(doc);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("¡Usuarios cargados correctamente en la coleccion!");
	}

	public static void cargarMazos(MongoDatabase database) {
		MongoCollection<Document> collection = database.getCollection("Mazos");

		collection.drop();

		JSONParser parser = new JSONParser();
		File f = new File("..\\UF3_LegendsOfRunaterra_UsonD\\src\\main\\resources\\Mazos.json");

		try {
			FileReader fr = new FileReader(f);
			JSONArray array = (JSONArray) parser.parse(fr);
			Iterator<?> iterator = array.iterator();

			while (iterator.hasNext()) {
				JSONObject object = (JSONObject) iterator.next();
				Mazos mazo = new Mazos();
				mazo.setId(Integer.parseInt(object.get("id_mazo").toString()));
				mazo.setNombre((String) object.get("nombre_mazo"));
				mazo.setValor(Integer.parseInt(object.get("valor_mazo").toString()));
				mazo.setListaCartas((ArrayList<Integer>) object.get("cartas_en_mazo"));

				Document doc = new Document("id_mazo", mazo.getId()).append("nombre_mazo", mazo.getNombre())
						.append("valor_mazo", mazo.getValor()).append("cartas_en_mazo", mazo.getListaCartas());
				collection.insertOne(doc);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		System.out.println("¡Mazos cargados correctamente en la coleccion!");
	}
	
	public static void menuPrincipal(MongoDatabase database) {
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
					System.out.println("\n||=====================||");
					System.out.println("|| LEAGUE OF RUNATERRA ||");
					System.out.println("||         FIN         ||");
					System.out.println("||=====================||");
					System.exit(0); 
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
					menuPrincipal(database);
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
					eliminarMazo(database, usuario);
					break;
				case 4:
					cargarMazosPredefinidos(database, usuario);
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
		
		Mazos m = new Mazos();
		ArrayList<Integer> cartas = new ArrayList<Integer>();
		
		ArrayList<Integer> listaCartas = new ArrayList<Integer>();
		ArrayList<Integer> listaMazos = new ArrayList<Integer>();
		
		System.out.println("||=====================||");
		System.out.println("|| LEAGUE OF RUNATERRA ||");
		System.out.println("||     Nuevo  Mazo     ||");
		System.out.println("||=====================||");
		
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");

		Document find = new Document("id", usuario.get("id"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId(d.getInteger("id"));
			u.setNombre(d.getString("nombre"));
			u.setContra(d.getString("contra"));
			if (d.get("listaCartas") != null) {
				listaCartas = (ArrayList<Integer>) d.get("listaCartas");
			}
			u.setListaCartas(listaCartas);
			if (d.get("listaMazos") != null) {
				listaMazos = (ArrayList<Integer>) d.get("listaMazos");
			}
			u.setListaMazos(listaMazos);
			
			System.out.println("  " + u.toString());
		}
		
		System.out.print("\nNombre del Mazo: ");
		String nombre = entrada.nextLine();
		m.setNombre(nombre);
		System.out.println("Lista de Cartas (-1 para salir):");
				
		int contador = 1;
		boolean existe = false;
		
		while (contador != 20) {
			System.out.print("  Introduce un ID de Carta: ");
			int id = entrada.nextInt();
			
			if (id == -1) {
				break;
			} else {
				// Si el ID de la Carta que introduce el usuario existe en la lista de Cartas que tiene el mismo, se añade al Mazo 
				for (Integer idCarta : listaCartas) {
					if (id == idCarta) {
						cartas.add(id);
						contador++;
						existe = true;
						break;
					}
				}
				if (existe == false) {
					System.err.println("El ID de la Carta introducido no existe, por favor intenta con otra.");
				}
			}
		}
		
		m.setListaCartas(cartas);
				
		// Meter las cartas en el usuario
		// Recoger el ultimo ID de Mazos que hay en la Base de Datos y sumarle 1 para añadir el nuevo
		MongoCollection<Document> collectionM = database.getCollection("Mazos");
		FindIterable<Document> docs = collectionM.find();
		
		int lastID = 0;
		for (Document document : docs) {
			//System.out.println(document);
			System.out.println(document.get("id") + " - " + document.get("nombre") + " - " + document.get("valor") + " - " + document.get("listaCartas"));
			lastID = document.getInteger("id");
		}
		
		m.setId(lastID+1);
		
		//	Filtros de Cartas del Mazo
		int precioTotal = 0;
		int minHeroes = 0;
		for (Integer idCarta : cartas) {
			Document findCarta = new Document("id", idCarta);
			MongoCursor<Document> resutlCarta = collectionM.find(findCarta).iterator();
			
			if (resutlCarta.hasNext()) {
				Document d = resutlCarta.next();
				Cartas c = new Cartas();
				
				c.setID(d.getInteger("id"));
				c.setTipo(d.getString("tipo"));
				c.setNombre(d.getString("nombre"));
				if (d.getInteger("coste") != null) {
					c.setCoste(d.getInteger("coste"));
				} else {
					c.setCoste(0);
				}
				if (d.getInteger("ataque") != null) {
					c.setAtaque(d.getInteger("ataque"));
				} else {
					c.setAtaque(0);
				}
				
				if (d.getInteger("vida") != null) {
					c.setVida(d.getInteger("vida"));
				} else {
					c.setVida(0);
				}
				c.setHabilidadEspecial(d.getString("habilidadEspecial"));
				c.setFaccion(d.getString("faccion"));
				
				precioTotal = precioTotal + c.getCoste();
				
				if (c.getTipo().equalsIgnoreCase("Heroe")) {
					minHeroes++;
				}
				
				System.out.println("  " + c.toString());
			}
		}
		
		if (precioTotal <= 60) {
			if (minHeroes >= 2) {
				m.setValor(precioTotal);
				
				// Hacer un Insert del Mazo a la Base de Datos
				Document document = new Document();
				document.put("id", m.getId());
				document.put("nombre", m.getNombre());
				document.put("valor", m.getValor());
				document.put("listaCartas", m.getListaCartas());
				
				collectionM.insertOne(document);
				
				// Actualizar la Lista de Mazos del Usuario con el nuevo ID del Mazo		
				listaMazos.add(m.getId());
				
				Document query = new Document("id", u.getId());
				Document newDoc = new Document("listaMazos", listaMazos);
				Document updateDoc = new Document("$set", newDoc);
				
				collectionU.updateOne(query, updateDoc);
			}
		}
		
		
	}
	
	public static void eliminarMazo(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("||=====================||");
		System.out.println("|| LEAGUE OF RUNATERRA ||");
		System.out.println("||     Borrar  Mazo    ||");
		System.out.println("||=====================||");
		
		ArrayList<Integer> listaCartas = new ArrayList<Integer>();
		ArrayList<Integer> listaMazos = new ArrayList<Integer>();
		
		// Guardar el usuario logueado
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");

		Document find = new Document("id", usuario.get("id"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId(d.getInteger("id"));
			u.setNombre(d.getString("nombre"));
			u.setContra(d.getString("contra"));
			listaCartas = (ArrayList<Integer>) d.get("listaCartas");
			u.setListaCartas(listaCartas);
			listaMazos = (ArrayList<Integer>) d.get("listaMazos");
			u.setListaMazos(listaMazos);
			
			System.out.println("  " + u.toString());
		}
		
		// Recoger el ID del Mazo a eliminar
		System.out.print("\nInserte el ID del mazo que quiere eliminar: ");
		int id = entrada.nextInt();
		
		// Recorrer la lista de Mazos para eliminar el que ha introducido el usuario
		for (Integer idMazo : listaMazos) {
			if (id == idMazo) {
				int index = listaMazos.indexOf(id);
				listaMazos.remove(index);
				
				Document query = new Document("id", u.getId());
				Document newDoc = new Document("listaMazos", listaMazos);
				Document updateDoc = new Document("$set", newDoc);
				
				collectionU.updateOne(query, updateDoc);
				
				MongoCollection<Document> collectionM = database.getCollection("Mazos");
				Document d = new Document("id", id);
				((Document) collectionM).remove(d);
			}
		}
	}
	
	public static void cargarMazosPredefinidos(MongoDatabase database, Document usuario) {
		// Buscar en la base de datos los Mazos con ID 1 y 2
		// Tengo que cambiar el metodo de borrar, para que no me borre estos 2 Mazos
		
		System.out.println("Cargando los Mazos Predefinidos...");
		
		ArrayList<Integer> listaCartas = new ArrayList<Integer>();
		ArrayList<Integer> listaMazos = new ArrayList<Integer>();
		
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");

		Document find = new Document("id", usuario.get("id"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId(d.getInteger("id"));
			u.setNombre(d.getString("nombre"));
			u.setContra(d.getString("contra"));
			listaCartas = (ArrayList<Integer>) d.get("listaCartas");
			u.setListaCartas(listaCartas);
			listaMazos = (ArrayList<Integer>) d.get("listaMazos");
			u.setListaMazos(listaMazos);
			
			System.out.println("  " + u.toString());
		}
		
		MongoCollection<Document> collectionM = database.getCollection("Mazos");
		FindIterable<Document> docs = collectionM.find();
		
		for (Document document : docs) {
			if (document.get("id").equals(1) || document.get("id").equals(2)) {
				System.out.println(document.get("id") + " - " + document.get("nombre") + " - " + document.get("valor") + " - " + document.get("listaCartas"));
				// Falta saber si en el la lista de Mazos, le usuario tiene ya los mazos predefinidos
				listaMazos.add(document.getInteger("id"));
			}
		}
		
		// Hacer el update
		Document query = new Document("id", u.getId());
		Document newDoc = new Document("listaMazos", listaMazos);
		Document updateDoc = new Document("$set", newDoc);
		
		collectionU.updateOne(query, updateDoc);
		
		System.out.println("\nSe han cargado los Mazos correctamente.");
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
					comprar(database, usuario);
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
	
	public static void comprar(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		MongoCollection<Document> collectionM = database.getCollection("Cartas");
		FindIterable<Document> docs = collectionM.find();
				
		int saltoDeLinea = 0;
		System.out.println("");
		for (Document document : docs) {
			System.out.println(document.get("id") + " - " + document.get("tipo") + " - " + document.get("nombre") + " - " + document.get("coste") + " - " + document.get("ataque") + " - " + document.get("vida") + " - " + document.get("habilidadEspecial") + " - " + document.get("faccion"));
			saltoDeLinea++;
			if (saltoDeLinea == 6) {
				System.out.println("");
			}
		}
		
		System.out.println("");
		
		boolean continuar = true;
		System.out.println("Introduce el ID de la carta que quieras comprar (-1 para salir):");
		while (continuar) {
			System.out.print("	--> ");
			int id = entrada.nextInt();
			
			if (id == -1) {
				break;
			}
			
			boolean coincide = false;
			for (Document document : docs) {
				if (id == document.getInteger("id")) {
					coincide = true;
				}
			}
			if (coincide == false) {
				System.err.println("No existe esa Carta. Prueba con otra.");
			}
		}
	}
	
}