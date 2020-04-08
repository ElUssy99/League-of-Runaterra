package Objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Usuarios {
	
	private int id_usuario;
	private String nombre_usuario;
	private String contrasenya_usuario;
	private ArrayList<Integer> cartas_usuario;
	private ArrayList<Integer> mazos_usuario;
	
	public Usuarios() {
		super();
	}

	public Usuarios(int id_usuario, String nombre_usuario, String contrasenya_usuario,
			ArrayList<Integer> cartas_usuario, ArrayList<Integer> mazos_usuario) {
		super();
		this.id_usuario = id_usuario;
		this.nombre_usuario = nombre_usuario;
		this.contrasenya_usuario = contrasenya_usuario;
		this.cartas_usuario = cartas_usuario;
		this.mazos_usuario = mazos_usuario;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getContrasenya_usuario() {
		return contrasenya_usuario;
	}

	public void setContrasenya_usuario(String contrasenya_usuario) {
		this.contrasenya_usuario = contrasenya_usuario;
	}

	public ArrayList<Integer> getCartas_usuario() {
		return cartas_usuario;
	}

	public void setCartas_usuario(ArrayList<Integer> cartas_usuario) {
		this.cartas_usuario = cartas_usuario;
	}

	public ArrayList<Integer> getMazos_usuario() {
		return mazos_usuario;
	}

	public void setMazos_usuario(ArrayList<Integer> mazos_usuario) {
		this.mazos_usuario = mazos_usuario;
	}

	@Override
	public String toString() {
		return "Usuarios [id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", contrasenya_usuario="
				+ contrasenya_usuario + ", cartas_usuario=" + cartas_usuario + ", mazos_usuario=" + mazos_usuario + "]";
	}
	
}
