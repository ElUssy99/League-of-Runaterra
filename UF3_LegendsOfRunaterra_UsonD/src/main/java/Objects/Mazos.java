package Objects;

import java.util.ArrayList;

public class Mazos {
	
	private String id;
	private String nombre;
	private int valor;
	private ArrayList<Integer> listaCartas;
		
	public Mazos() {}
	
	public Mazos(String id, String nombre, int valor, ArrayList<Integer> listaCartas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.valor = valor;
		this.listaCartas = listaCartas;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public ArrayList<Integer> getListaCartas() {
		return listaCartas;
	}
	public void setListaCartas(ArrayList<Integer> listaCartas) {
		this.listaCartas = listaCartas;
	}

	@Override
	public String toString() {
		return "Mazos [id=" + id + ", nombre=" + nombre + ", valor=" + valor + ", listaCartas="
				+ listaCartas + "]";
	}
	
}
