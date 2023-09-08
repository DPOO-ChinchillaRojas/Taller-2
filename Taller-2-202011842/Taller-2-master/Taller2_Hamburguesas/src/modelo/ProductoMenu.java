package modelo;

public class ProductoMenu implements Producto
{
	/************************************************
	 * ATRIBUTOS
	 ************************************************/
	private String nombre; /// El nombre del producto del menú
	private int precioBase; /// El precio del producto por sí mismo, sin adiciones
	
	
	/*************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public ProductoMenu(String nombre, int precioBase) 
	/// Construye una clase ProductoMenu dado su nombre y su precio de menú
	{
		this.nombre = nombre; /// Asigna el nombre al atributo nombre
		this.precioBase = precioBase; /// Asigna el precio de menú al atributo precioBase
	}
	
	
	/*************************************************
	 * MÉTODOS
	 ************************************************/
	@Override
	public int getPrecio() 
	/// Retorna el precio del producto del menú. HEREDADO DE LA INTERFAZ
	{
		return precioBase;
	}


	@Override
	public String getNombre()
	/// Retorna el nombre del producto del menú. HEREDADO DE LA INTERFAZ
	{
		return nombre;
	}


	@Override
	public String generarTextoFactura() 
	/// Genera el texto que se imprime en la factura. HEREDADO DE LA INTERFAZ
	{
		/// Devuelve el nombre del producto base y su precio individual
		String texto = String.format("%-20s %10s", nombre, precioBase) + "\n";
		/// Se emplea el formateo de Strings en Java
		return texto;
	}
}



