package modelo;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto
{
	/************************************************
	 * ATRIBUTOS ** Cambiado - agregado atributo componentesCombo **
	 ************************************************/
	private double descuento;
	private String nombreCombo;
	private List<Producto> componentesCombo;


	/*************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public Combo(String nombre, double descuento)
	/// Construye una clase Combo dado un nombre y un descuento
	{
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.componentesCombo = new ArrayList<Producto>();
	}


	/*************************************************
	 * MÉTODOS
	 ************************************************/
	public void agregarItemACombo(Producto itemCombo)
	/// Adiciona un producto del menú al combo
	{
		/// Agrega un item a la lista de productos en el combo
		componentesCombo.add(itemCombo);
	}


	@Override
	public int getPrecio()
	/// Calcula el precio del combo a partir de una operación
	{
		int precioCalculado = 0;
		/// Calcula la suma de los precios de todos los componentes del combo
		for (Producto componente : componentesCombo) 
		{
			precioCalculado += componente.getPrecio();
		}
		/// Calcula el descuento sobre el precio total según el porcentaje
		precioCalculado -= (descuento/100)*precioCalculado;
		return (int)precioCalculado;
	}


	@Override
	public String getNombre()
	/// Retorna el nombre del combo en cuestión
	{
		return nombreCombo;
	}


	@Override
	public String generarTextoFactura()
	/// Genera el texto de factura del combo
	{
		/// Devuelve el nombre del combo y su precio definitivo
		String formal = String.format("%-20s %10s", nombreCombo, String.valueOf(getPrecio())) + "\n";
		String componentes = "";
		for (Producto componente : componentesCombo)
		{
			componentes += componente.getNombre() + "\n";
		}
		/// Se emplea el formateo de Strings en Java
		String texto = formal + componentes;
		return texto;
	}
}

