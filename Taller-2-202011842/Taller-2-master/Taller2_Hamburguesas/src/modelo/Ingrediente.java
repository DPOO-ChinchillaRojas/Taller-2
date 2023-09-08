package modelo;

public class Ingrediente 
{
	/************************************************
	 * ATRIBUTOS
	 ************************************************/
	private String nombre; /// El nombre del ingrediente
	private int costoAdicional; /// El costo del ingrediente cuando es adicionado
	
	
	/*************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public Ingrediente(String nombre, int costoAdicional) 
	/// Construye una clase Ingrediente dado su nombre y su costo
	{
		this.nombre = nombre; /// Asigna el nombre al atributo nombre
		this.costoAdicional = costoAdicional; /// Asigna el costo al atributo costoAdicional
	}
	
	
	/*************************************************
	 * MÉTODOS
	 ************************************************/
	public String getNombre() 
	/// Retorna el nombre del ingrediente
	{
		return nombre;
	}
	
	public int getCostoAdicional() 
	/// Retorna el costo del ingrediente cuando es adicionado
	{
		return costoAdicional;
	}
}


