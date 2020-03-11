package Objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Usuarios {
	
	private int id;
	private String nombre;
	private String contra;
	private ArrayList<Integer> listaCartas;
	private ArrayList<Integer> listaMazos;
	
	public Usuarios() {
		super();
	}

	public Usuarios(int id, String nombre, String contra, ArrayList<Integer> listaCartas, ArrayList<Integer> listaMazos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contra = contra;
		this.listaCartas = listaCartas;
		this.listaMazos = listaMazos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}
	
	public ArrayList<Integer> getListaCartas() {
		return listaCartas;
	}

	public void setListaCartas(ArrayList<Integer> listaCartas) {
		this.listaCartas = listaCartas;
	}

	public ArrayList<Integer> getListaMazos() {
		return listaMazos;
	}

	public void setListaMazos(ArrayList<Integer> listaMazos) {
		this.listaMazos = listaMazos;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombre=" + nombre + ", contra=" + contra + ", listaCartas=" + listaCartas
				+ ", listaMazos=" + listaMazos + "]";
	}
	
}
