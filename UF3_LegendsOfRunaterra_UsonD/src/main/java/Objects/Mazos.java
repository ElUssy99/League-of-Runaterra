package Objects;

import java.util.ArrayList;

public class Mazos {
	
	private int id_mazo;
	private String nombre_mazo;
	private int valor_mazo;
	private ArrayList<Integer> cartas_en_mazo;
		
	public Mazos() {}

	public Mazos(int id_mazo, String nombre_mazo, int valor_mazo, ArrayList<Integer> cartas_en_mazo) {
		super();
		this.id_mazo = id_mazo;
		this.nombre_mazo = nombre_mazo;
		this.valor_mazo = valor_mazo;
		this.cartas_en_mazo = cartas_en_mazo;
	}

	public int getId_mazo() {
		return id_mazo;
	}

	public void setId_mazo(int id_mazo) {
		this.id_mazo = id_mazo;
	}

	public String getNombre_mazo() {
		return nombre_mazo;
	}

	public void setNombre_mazo(String nombre_mazo) {
		this.nombre_mazo = nombre_mazo;
	}

	public int getValor_mazo() {
		return valor_mazo;
	}

	public void setValor_mazo(int valor_mazo) {
		this.valor_mazo = valor_mazo;
	}

	public ArrayList<Integer> getCartas_en_mazo() {
		return cartas_en_mazo;
	}

	public void setCartas_en_mazo(ArrayList<Integer> cartas_en_mazo) {
		this.cartas_en_mazo = cartas_en_mazo;
	}

	@Override
	public String toString() {
		return "Mazos [id_mazo=" + id_mazo + ", nombre_mazo=" + nombre_mazo + ", valor_mazo=" + valor_mazo
				+ ", cartas_en_mazo=" + cartas_en_mazo + "]";
	}
	
}
