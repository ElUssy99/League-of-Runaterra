package Objects;

public class Cartas {
	
	private int ID;
	private String tipo;
	private String nombre;
	private int coste;
	private int ataque;
	private int vida;
	private String habilidadEspecial;
	private String faccion;
	
	public Cartas() {}
	
	public Cartas(int iD, String tipo, String nombre, int coste, int ataque, int vida, String habilidadEspecial,
			String faccion) {
		super();
		ID = iD;
		this.tipo = tipo;
		this.nombre = nombre;
		this.coste = coste;
		this.ataque = ataque;
		this.vida = vida;
		this.habilidadEspecial = habilidadEspecial;
		this.faccion = faccion;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCoste() {
		return coste;
	}
	public void setCoste(int coste) {
		this.coste = coste;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public String isHabilidadEspecial() {
		return habilidadEspecial;
	}
	public void setHabilidadEspecial(String habilidadEspecial) {
		this.habilidadEspecial = habilidadEspecial;
	}
	public String getFaccion() {
		return faccion;
	}
	public void setFaccion(String faccion) {
		this.faccion = faccion;
	}
	
	@Override
	public String toString() {
		return "Carta [ID=" + ID + ", tipo=" + tipo + ", nombre=" + nombre + ", coste=" + coste + ", ataque=" + ataque
				+ ", vida=" + vida + ", habilidadEspecial=" + habilidadEspecial + ", faccion=" + faccion + "]";
	}
	
}
