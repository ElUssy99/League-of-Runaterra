package Principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
		cargarMazos(database);
		
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
		boolean mensaje = false;
		
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
				
				// Comprobar si existen los mazos antes de insertarlos en la Base de Datos
				FindIterable<Document> docs = collection.find();

				boolean mazo1 = false;
				boolean mazo2 = false;
				for (Document document : docs) {
					if (document.getInteger("id_mazo") == 1) {
						mazo1 = true;
					}
					if (document.getInteger("id_mazo") == 2) {
						mazo2 = true;
					}
				}
				
				if (mazo1 == false) {
					if (doc.getInteger("id_mazo") == 1) {
						collection.insertOne(doc);
						mensaje = true;
					}
				}
				if (mazo2 == false) {
					if (doc.getInteger("id_mazo") == 2) {
						collection.insertOne(doc);
						mensaje = true;
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		if (mensaje == true) {
			System.out.println("¡Mazos cargados correctamente en la coleccion!");
		}
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
			System.out.println("	3. Cargar mazos predefinidos");
			System.out.println("	4. <-- Atras");
			System.out.print("Escoge una opcion: ");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
				case 1:
					nuevoMazo(database, usuario);
					break;
				case 2:
					editarMazo(database, usuario);
					break;
				case 3:
					cargarMazosPredefinidos(database, usuario);
					break;
				case 4:
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
				// Si el ID de la Carta que introduce el usuario existe en la lista de Cartas que tiene el mismo, se añade al Mazo
				for (Number idCarta : listaCartas) {
					if (id == idCarta.intValue()) {
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
		
		m.setCartas_en_mazo(cartas);
				
		// Meter las cartas en el usuario
		// Recoger el ultimo ID de Mazos que hay en la Base de Datos y sumarle 1 para añadir el nuevo
		FindIterable<Document> docs = collectionM.find();
		
		int lastID = 0;
		for (Document document : docs) {
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
	
	public static void editarMazo(MongoDatabase database, Document usuario) {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("||=====================||");
		System.out.println("|| LEAGUE OF RUNATERRA ||");
		System.out.println("||    Editar Mazos     ||");
		System.out.println("||=====================||\n");
		
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
		
		String opcion = "";
		String nuevoNombre = "";
		
		System.out.println("ID de Mazos:");
		for (Integer integer : listaMazos) {
			System.out.print(integer + " ");
		}
		
		System.out.print("Introduce el ID del Mazo que quieres editar: ");
		int idMazo = entrada.nextInt();
		
		ArrayList<Integer> listaCartasMazo = new ArrayList<Integer>();
		
		MongoCollection<Document> collectionM = database.getCollection("Usuarios");
		
		Document findM = new Document("id_mazo", idMazo);
		MongoCursor<Document> resutlM = collectionU.find(find).iterator();
		
		Mazos m = new Mazos();
		if (resutlM.hasNext()) {
			Document d = resutlM.next();
			
			m.setId_mazo(d.getInteger("id_mazo"));
			m.setNombre_mazo(d.getString("nombre_mazo"));
			m.setValor_mazo(d.getInteger("valor_mazo"));
			if (d.get("cartas_en_mazo") != null) {
				listaCartasMazo = (ArrayList<Integer>) d.get("cartas_en_mazo");
			}
			u.setCartas_usuario(listaCartasMazo);
			
			System.out.println("\n  " + u.toString());
		}
		
		System.out.println("\nEditar nombre? (Si/No)");
		opcion = entrada.nextLine();
		
		if (opcion.equalsIgnoreCase("si")) {
			System.out.print("\nIntroduce el nuevo nombre del Mazo: ");
			nuevoNombre = entrada.nextLine();
		}
		
		System.out.println("\nQuieres editar las cartas del Mazo? (Si/No)");
		opcion = entrada.nextLine();
		
		if (opcion.equalsIgnoreCase("si")) {
			boolean editar = true;
			
			while (editar) {
				System.out.println("Eliminar (1) | Añadir (2) | Acabar (-1):");
				int opcionMazo = entrada.nextInt();
				
				if (opcionMazo == 1) {
					boolean continuar = true;
					boolean existe = false;
					
					for (Number integer : listaCartasMazo) {
						System.out.print(integer.intValue() + " ");
					}
					
					while (continuar) {
						System.out.print("  Introduce un ID de Carta (-1 acabar): ");
						int id = entrada.nextInt();
						
						if (id == -1) {
							continuar = false;
							break;
						} else {
							for (Number idCarta : listaCartasMazo) {
								if (id == idCarta.intValue()) {
									listaCartasMazo.remove(id);
									existe = true;
									break;
								}
							}
							
							if (existe == false) {
								System.err.println("El ID de la Carta introducido no existe, por favor intenta con otra.");
							}
						}
					}
				} else if (opcionMazo == 2) {
					boolean continuar = true;
					boolean existe = false;
					
					for (Number integer : listaCartas) {
						System.out.print(integer.intValue() + " ");
					}
					
					while (continuar) {
						System.out.print("  Introduce un ID de Carta (-1 acabar): ");
						int id = entrada.nextInt();
						
						if (id == -1) {
							continuar = false;
							break;
						} else {
							for (Number idCarta : listaCartas) {
								if (id == idCarta.intValue()) {
									listaCartasMazo.add(id);
									existe = true;
									break;
								}
							}
							
							if (existe == false) {
								System.err.println("El ID de la Carta introducido no existe, por favor intenta con otra.");
							}
						}
					}
				} else if (opcionMazo == -1) {
					editar = false;
					break;
				}
			}
			
			// Hacer el update nombre
			Document queryNombre = new Document("id_mazo", m.getId_mazo());
			Document newDocNombre = new Document("cartas_de_mazo", listaCartasMazo);
			Document updateDocNombre = new Document("$set", newDocNombre);
			
			collectionM.updateOne(queryNombre, updateDocNombre);
			
			// Hacer el update cartas
			Document queryCartas = new Document("id_mazo", m.getId_mazo());
			Document newDocCartas = new Document("cartas_de_mazo", listaCartasMazo);
			Document updateDocCartas = new Document("$set", newDocCartas);
			
			collectionM.updateOne(queryCartas, updateDocCartas);
		}
	}
	
	public static void cargarMazosPredefinidos(MongoDatabase database, Document usuario) {
		// Buscar en la base de datos los Mazos con ID 1 y 2
		
		System.out.println("Cargando los Mazos Predefinidos...\n");
		
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
				listaMazos.add(document.getInteger("id_mazo"));
			}
		}
		
		// Hacer el update
		Document query = new Document("id_usuario", u.getId_usuario());
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
			System.out.println("	" + document.get("id") + " - " + document.get("tipo") + " - " + document.get("nombre_carta") + " - " + document.get("coste_invocacion") + " - " + document.get("ataque") + " - " + document.get("vida") + " - " + document.get("habilidad_especial") + " - " + document.get("faccion"));
			saltoDeLinea++;
			if (saltoDeLinea == 6) {
				System.out.println("");
				saltoDeLinea = 0;
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
				System.err.println("	No existe esa Carta. Prueba con otra.");
			}
		}
		
		// Update de la lista de cartas del Usuario
		Document query = new Document("id_usuario", u.getId_usuario());
		Document newDoc = new Document("cartas_usuario", listaCartas);
		Document updateDoc = new Document("$set", newDoc);
		
		collectionU.updateOne(query, updateDoc);
		
		System.out.println("\nSe ha actualizado la Lista de Cartas correctamente.");
	}
	
}