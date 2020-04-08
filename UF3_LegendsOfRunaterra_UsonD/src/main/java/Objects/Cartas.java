package Objects;

public class Cartas {
	
	private int id;
	private String tipo;
	private String nombre_carta;
	private int coste_invocacion;
	private int ataque;
	private int vida;
	private String habilidad_especial;
	private String faccion;
	
	public Cartas() {}

	public Cartas(int id, String tipo, String nombre_carta, int coste_invocacion, int ataque, int vida,
			String habilidad_especial, String faccion) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre_carta = nombre_carta;
		this.coste_invocacion = coste_invocacion;
		this.ataque = ataque;
		this.vida = vida;
		this.habilidad_especial = habilidad_especial;
		this.faccion = faccion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre_carta() {
		return nombre_carta;
	}

	public void setNombre_carta(String nombre_carta) {
		this.nombre_carta = nombre_carta;
	}

	public int getCoste_invocacion() {
		return coste_invocacion;
	}

	public void setCoste_invocacion(int coste_invocacion) {
		this.coste_invocacion = coste_invocacion;
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

	public String getHabilidad_especial() {
		return habilidad_especial;
	}

	public void setHabilidad_especial(String habilidad_especial) {
		this.habilidad_especial = habilidad_especial;
	}

	public String getFaccion() {
		return faccion;
	}

	public void setFaccion(String faccion) {
		this.faccion = faccion;
	}

	@Override
	public String toString() {
		return "Cartas [id=" + id + ", tipo=" + tipo + ", nombre_carta=" + nombre_carta + ", coste_invocacion="
				+ coste_invocacion + ", ataque=" + ataque + ", vida=" + vida + ", habilidad_especial="
				+ habilidad_especial + ", faccion=" + faccion + "]";
	}
	
}
