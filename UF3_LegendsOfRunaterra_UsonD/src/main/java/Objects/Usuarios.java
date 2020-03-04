package Objects;

import java.util.Arrays;

public class Usuarios {
	
	private int id;
	private String nombre;
	private String contra;
	private Cartas[] listaCartas;
	private Mazos[] listaMazos;
	
	public Usuarios() {
		super();
	}

	public Usuarios(int id, String nombre, String contra, Cartas[] listaCartas, Mazos[] listaMazos) {
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
	
	public Cartas[] getListaCartas() {
		return listaCartas;
	}

	public void setListaCartas(Cartas[] listaCartas) {
		this.listaCartas = listaCartas;
	}

	public Mazos[] getListaMazos() {
		return listaMazos;
	}

	public void setListaMazos(Mazos[] listaMazos) {
		this.listaMazos = listaMazos;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombre=" + nombre + ", contra=" + contra + ", listaCartas="
				+ Arrays.toString(listaCartas) + ", listaMazos=" + Arrays.toString(listaMazos) + "]";
	}
	
}
