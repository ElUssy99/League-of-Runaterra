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
		
		//cargarUsuarios(database);
		cargarCartas(database);
		//cargarMazos(database);
		
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
				carta.setId(Integer.parseInt(object.get("id").toString()));
				carta.setTipo((String) object.get("tipo"));
				carta.setNombre_carta((String) object.get("nombre_carta"));
				carta.setCoste_invocacion(Integer.parseInt(object.get("coste_invocacion").toString()));
				carta.setAtaque(Integer.parseInt(object.get("ataque").toString()));
				carta.setVida(Integer.parseInt(object.get("vida").toString()));
				carta.setHabilidad_especial((String) object.get("habilidad_especial"));
				carta.setFaccion((String) object.get("faccion"));

				Document doc = new Document("id", carta.getId()).append("tipo", carta.getTipo())
						.append("nombre_carta", carta.getNombre_carta())
						.append("coste_invocacion", carta.getCoste_invocacion()).append("ataque", carta.getAtaque())
						.append("vida", carta.getVida()).append("habilidad_especial", carta.getHabilidad_especial())
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
				usuario.setId_usuario(Integer.parseInt(object.get("id_usuario").toString()));
				usuario.setContrasenya_usuario((String) object.get("contrasenya_usuario"));
				usuario.setNombre_usuario((String) object.get("nombre_usuario"));
				usuario.setMazos_usuario((ArrayList<Integer>) object.get("mazos_usuario"));
				usuario.setCartas_usuario((ArrayList<Integer>) object.get("cartas_usuario"));

				Document doc = new Document("id_usuario", usuario.getId_usuario())
						.append("contrasenya_usuario", usuario.getContrasenya_usuario())
						.append("nombre_usuario", usuario.getNombre_usuario())
						.append("mazos_usuario", usuario.getMazos_usuario())
						.append("cartas_usuario", usuario.getCartas_usuario());
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
				mazo.setId_mazo(Integer.parseInt(object.get("id_mazo").toString()));
				mazo.setNombre_mazo((String) object.get("nombre_mazo"));
				mazo.setValor_mazo(Integer.parseInt(object.get("valor_mazo").toString()));
				mazo.setCartas_en_mazo((ArrayList<Integer>) object.get("cartas_en_mazo"));

				Document doc = new Document("id_mazo", mazo.getId_mazo()).append("nombre_mazo", mazo.getNombre_mazo())
						.append("valor_mazo", mazo.getValor_mazo()).append("cartas_en_mazo", mazo.getCartas_en_mazo());
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
			System.out.println(document.get("id_usuario") + " - " + document.get("nombre_usuario") + " - " + document.get("contrasenya_usuario") + " - " + document.get("cartas_usuario") + " - " + document.get("mazos_usuario"));
		}
		
		System.out.println("\n--Login--");
		System.out.print("Usuario: ");
		String name = entrada.next();
		System.out.print("Contraseña: ");
		String pass = entrada.next();
		
		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		
		for (Document document : docs) {
			if (name.equalsIgnoreCase(document.getString("nombre_usuario"))) {
				if (pass.equalsIgnoreCase(document.getString("contrasenya_usuario"))) {
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
			System.out.println("	3. Tus cartas");
			System.out.println("	4. Log out");
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
					
					break;
				case 4:
					continuar = false;
					menuPrincipal(database);
					break;
				default:
					break;
				}
		}
	}
	
	/*public static void tusCartas(MongoDatabase database, Document usuario) {
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");

		Document find = new Document("id_usuario", usuario.get("id_usuario"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId_usuario(d.getInteger("id_usuario"));
			u.setNombre_usuario(d.getString("nombre_usuario"));
			u.setContrasenya_usuario(d.getString("contrasenya_usuario"));
			if (d.get("cartas_usuario") != null) {
				listaCartas = (ArrayList<Integer>) d.get("cartas_usuario");
			}
			u.setCartas_usuario(listaCartas);
			if (d.get("mazos_usuario") != null) {
				listaMazos = (ArrayList<Integer>) d.get("mazos_usuario");
			}
			u.setMazos_usuario(listaMazos);
			
			System.out.println("  " + u.toString());
		}
	}*/
	
	public static void mazos(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("||=====================||");
			System.out.println("|| LEAGUE OF RUNATERRA ||");
			System.out.println("||        Mazos        ||");
			System.out.println("||=====================||");
			System.out.println("	1. Nuevo mazo");
			System.out.println("	2. Editar mazo (No funcional)");
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
					System.out.println("Lo sentimos pero en estos momentos no se pueden editar los mazos. Vuelva en otro momento.");
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
		
		String[] facciones = {"Demacia", "Moxus", "Piltover y Zaun", "Islas de la Sombra", "Jonia", "Freldjord"};
		
		Mazos m = new Mazos();
		ArrayList<Integer> cartas = new ArrayList<Integer>();
		
		ArrayList<Integer> listaCartas = new ArrayList<Integer>();
		ArrayList<Integer> listaMazos = new ArrayList<Integer>();
		
		System.out.println("||=====================||");
		System.out.println("|| LEAGUE OF RUNATERRA ||");
		System.out.println("||     Nuevo  Mazo     ||");
		System.out.println("||=====================||");
		
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");
		MongoCollection<Document> collectionC = database.getCollection("Cartas");
		MongoCollection<Document> collectionM = database.getCollection("Mazos");

		Document find = new Document("id_usuario", usuario.get("id_usuario"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId_usuario(d.getInteger("id_usuario"));
			u.setNombre_usuario(d.getString("nombre_usuario"));
			u.setContrasenya_usuario(d.getString("contrasenya_usuario"));
			if (d.get("cartas_usuario") != null) {
				listaCartas = (ArrayList<Integer>) d.get("cartas_usuario");
			}
			u.setCartas_usuario(listaCartas);
			if (d.get("mazos_usuario") != null) {
				listaMazos = (ArrayList<Integer>) d.get("mazos_usuario");
			}
			u.setMazos_usuario(listaMazos);
			
			System.out.println("  " + u.toString());
		}
		
		System.out.print("\nNombre del Mazo: ");
		String nombre = entrada.nextLine();
		m.setNombre_mazo(nombre);
		System.out.println("Lista de Cartas (-1 para salir):");
				
		int contador = 1;
		boolean existe = false;
		
		while (contador != 20) {
			System.out.print("  Introduce un ID de Carta: ");
			int id = entrada.nextInt();
			
			if (id == -1) {
				break;
			} else {
				cartas.add(id);
				
				// Peta porque al parecer la listaCartas me lo compara como Long
				// Si el ID de la Carta que introduce el usuario existe en la lista de Cartas que tiene el mismo, se añade al Mazo 
//				for (Integer idCarta : listaCartas) {
//					if (id == idCarta) {
//						
//						contador++;
//						existe = true;
//						break;
//					}
//				}
//				
//				if (existe == false) {
//					System.err.println("El ID de la Carta introducido no existe, por favor intenta con otra.");
//				}
			}
		}
		
		m.setCartas_en_mazo(cartas);
				
		// Meter las cartas en el usuario
		// Recoger el ultimo ID de Mazos que hay en la Base de Datos y sumarle 1 para añadir el nuevo
		FindIterable<Document> docs = collectionM.find();
		
		int lastID = 0;
		for (Document document : docs) {
			//System.out.println(document);
			System.out.println(document.get("id_mazo") + " - " + document.get("nombre_mazo") + " - " + document.get("valor_mazo") + " - " + document.get("cartas_en_mazo"));
			lastID = document.getInteger("id_mazo");
		}
		
		m.setId_mazo(lastID+1);
		
		// Filtros de Cartas del Mazo
		ArrayList<String> faccionesMazo = new ArrayList<String>();
		int costeInvocacione = 0;
		int heroes = 0;
		for (Integer idCarta : cartas) {
			Document findCarta = new Document("id", idCarta);
			MongoCursor<Document> resutlCarta = collectionM.find(findCarta).iterator();
			
			if (resutlCarta.hasNext()) {
				Document d = resutlCarta.next();
				Cartas c = new Cartas();
				
				c.setId(d.getInteger("id"));
				c.setTipo(d.getString("tipo"));
				c.setNombre_carta(d.getString("nombre_carta"));
				if (d.getInteger("coste_invocacion") != null) {
					c.setCoste_invocacion(d.getInteger("coste_invocacion"));
				} else {
					c.setCoste_invocacion(0);
				}
				costeInvocacione = costeInvocacione + c.getCoste_invocacion();
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
				c.setHabilidad_especial(d.getString("habilidad_especial"));
				c.setFaccion(d.getString("faccion"));
				faccionesMazo.add(d.getString("faccion"));
				if (c.getTipo().equalsIgnoreCase("Heroe")) {
					heroes++;
				}
				
				System.out.println("  " + c.toString());
			}
		}
		
		// Array de facciones boolean: DEMACIA, NOXUS, PILTOVER, ISLAS, JONIA, FRELDJORD
		boolean[] faccionesComp = {false, false, false, false, false, false};
		for (int i = 0; i < facciones.length; i++) {
			for (String fac : faccionesMazo) {
				if (facciones[i].equalsIgnoreCase(fac)) {
					if (fac.equalsIgnoreCase(facciones[0])) {
						faccionesComp[0] = true;
					} else if (fac.equalsIgnoreCase(facciones[1])) {
						faccionesComp[1] = true;
					} else if (fac.equalsIgnoreCase(facciones[2])) {
						faccionesComp[2] = true;
					} else if (fac.equalsIgnoreCase(facciones[3])) {
						faccionesComp[3] = true;
					} else if (fac.equalsIgnoreCase(facciones[4])) {
						faccionesComp[4] = true;
					} else if (fac.equalsIgnoreCase(facciones[5])) {
						faccionesComp[5] = true;
					}
				}
			}
		}
		
		int countFacciones = 0;
		for (int i = 0; i < faccionesComp.length; i++) {
			if (faccionesComp[i] == true) {
				countFacciones++;
			}
		}
		
		// Me falta maximo 2 cartas iguales.
		// Maximo cosete de invocacion
		if (costeInvocacione <= 60) {
			// Numero maximo de facciones
			if (countFacciones <= 2) {
				// Numero minimo de Heroes
				if (heroes >= countFacciones) {
					m.setValor_mazo(costeInvocacione);
					// Hacer un Insert del Mazo a la Base de Datos
					Document document = new Document();
					document.put("id_mazo", m.getId_mazo());
					document.put("nombre_mazo", m.getNombre_mazo());
					document.put("valor_mazo", m.getValor_mazo());
					document.put("cartas_en_mazo", m.getCartas_en_mazo());
					
					collectionM.insertOne(document);
					
					// Actualizar la Lista de Mazos del Usuario con el nuevo ID del Mazo		
					listaMazos.add(m.getId_mazo());
					
					Document query = new Document("id_usuario", u.getId_usuario());
					Document newDoc = new Document("mazos_usuario", listaMazos);
					Document updateDoc = new Document("$set", newDoc);
					
					collectionU.updateOne(query, updateDoc);
				} else {
					System.err.println("Lo siento, pero no cumple con la especificacion de 1 Heroes minimo por faccion.");
				}
			} else {
				System.err.println("Lo siento, pero no cumple con la especificacion del numero minimo de facciones (2).");
			}
		} else {
			System.err.println("Lo siento, pero no cumple con la especificacion del coste maximo de invocacion (60).");
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
			
			u.setId_usuario(d.getInteger("id_usuario"));
			u.setNombre_usuario(d.getString("nombre_usuario"));
			u.setContrasenya_usuario(d.getString("contrasenya_usuario"));
			listaCartas = (ArrayList<Integer>) d.get("cartas_usuario");
			u.setCartas_usuario(listaCartas);
			listaMazos = (ArrayList<Integer>) d.get("mazos_usuario");
			u.setMazos_usuario(listaMazos);
			
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
				
				Document query = new Document("id_usuario", u.getId_usuario());
				Document newDoc = new Document("mazos_usuario", listaMazos);
				Document updateDoc = new Document("$set", newDoc);
				
				collectionU.updateOne(query, updateDoc);
				
				MongoCollection<Document> collectionM = database.getCollection("Mazos");
				Document d = new Document("id_mazo", id);
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

		Document find = new Document("id_usuario", usuario.get("id_usuario"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId_usuario(d.getInteger("id_usuario"));
			u.setNombre_usuario(d.getString("nombre_usuario"));
			u.setContrasenya_usuario(d.getString("contrasenya_usuario"));
			listaCartas = (ArrayList<Integer>) d.get("cartas_usuario");
			u.setCartas_usuario(listaCartas);
			listaMazos = (ArrayList<Integer>) d.get("mazos_usuario");
			u.setMazos_usuario(listaMazos);
			
			System.out.println("  " + u.toString());
		}
		
		MongoCollection<Document> collectionM = database.getCollection("Mazos");
		FindIterable<Document> docs = collectionM.find();
		
		for (Document document : docs) {
			if (document.get("id_mazo").equals(1) || document.get("id_mazo").equals(2)) {
				System.out.println(document.get("id_mazo") + " - " + document.get("nombre_mazo") + " - " + document.get("valor_mazo") + " - " + document.get("cartas_en_mazo"));
				// Falta saber si en el la lista de Mazos, le usuario tiene ya los mazos predefinidos
				listaMazos.add(document.getInteger("id"));
			}
		}
		
		// Hacer el update
		Document query = new Document("id", u.getId_usuario());
		Document newDoc = new Document("mazos_usuario", listaMazos);
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
	
	// NO ME FUNCIONA BIEN EL UPDATE, NO SE POR QUE NO ME LO HACE
	public static void comprar(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		
		ArrayList<Integer> listaCartas = new ArrayList<Integer>();
		ArrayList<Integer> listaMazos = new ArrayList<Integer>();
		
		MongoCollection<Document> collectionU = database.getCollection("Usuarios");

		Document find = new Document("id_usuario", usuario.get("id_usuario"));
		MongoCursor<Document> resutl = collectionU.find(find).iterator();
		
		Usuarios u = new Usuarios();
		if (resutl.hasNext()) {
			Document d = resutl.next();
			
			u.setId_usuario(d.getInteger("id_usuario"));
			u.setNombre_usuario(d.getString("nombre_usuario"));
			u.setContrasenya_usuario(d.getString("contrasenya_usuario"));
			listaCartas = (ArrayList<Integer>) d.get("cartas_usuario");
			u.setCartas_usuario(listaCartas);
			listaMazos = (ArrayList<Integer>) d.get("mazos_usuario");
			u.setMazos_usuario(listaMazos);
			
			System.out.println("\n  " + u.toString());
		}
		
		MongoCollection<Document> collectionM = database.getCollection("Cartas");
		FindIterable<Document> docs = collectionM.find();
				
		int saltoDeLinea = 0;
		System.out.println("");
		for (Document document : docs) {
			System.out.println(document.get("id") + " - " + document.get("tipo") + " - " + document.get("nombre_carta") + " - " + document.get("coste_invocacion") + " - " + document.get("ataque") + " - " + document.get("vida") + " - " + document.get("habilidad_especial") + " - " + document.get("faccion"));
			saltoDeLinea++;
			if (saltoDeLinea == 6) {
				System.out.println("");
			}
		}
		
		System.out.println("");
		
		// Bucle para no parar de pedir un ID de carta hasta que el usuario lo cierre con un -1
		boolean continuar = true;
		System.out.println("Introduce el ID de la carta que quieras comprar (-1 para salir):");
		while (continuar) {
			System.out.print("	--> ");
			int id = entrada.nextInt();
			
			if (id == -1) {
				break;
			}
			
			// Si el ID coincide con alguna carta que exista en la Base de Datos, se añade a la lista de Cartas locla
			boolean coincide = false;
			for (Document document : docs) {
				if (id == document.getInteger("id")) {
					listaCartas.add(id);
					coincide = true;
				}
			}
			if (coincide == false) {
				System.err.println("No existe esa Carta. Prueba con otra.");
			}
		}
		
		// Update de la lista de cartas del Usuario
		Document query = new Document("id", u.getId_usuario());
		Document newDoc = new Document("cartas_usuario", listaCartas);
		Document updateDoc = new Document("$set", newDoc);
		
		collectionU.updateOne(query, updateDoc);
		
		System.out.println("\nSe ha actualizado la Lista de Cartas correctamente.");
	}
	
}