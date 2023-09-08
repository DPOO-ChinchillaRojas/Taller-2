package modelo;

import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado implements Producto
{
	/************************************************
	 * ATRIBUTOS *Cambiado respecto al diagrama de clases*
	 ************************************************/
	private ProductoMenu base;
	private List<Ingrediente> adicionados;
	private List<Ingrediente> eliminados;
	
	
	/*************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public ProductoAjustado(ProductoMenu base) 
	/// Construye una clase ProductoAjustado con una base de tipo ProductoMenu
	{
		/// Genera una variable donde almacena cuál es el producto base
		this.base = base;
		/// Se crean dos listas para ingredientes agregados y eliminados
		this.adicionados = new ArrayList<Ingrediente>();
		this.eliminados = new ArrayList<Ingrediente>();
	}
	
	/*************************************************
	 * MÉTODOS
	 ************************************************/
	@Override
	public int getPrecio() 
	/// Retorna el precio final del producto ajustado, contemplando adiciones
	{
		int precioAjustado = base.getPrecio();
		/// Establece un precio base y le va sumando según los ingredientes agregados
		for (Ingrediente ingrediente : adicionados) 
		{
			precioAjustado += ingrediente.getCostoAdicional();
		}
		return precioAjustado;
	}
	
	public void adicionar(Ingrediente ing) 
	{
			adicionados.add(ing);
			
	}
	public void eliminar(Ingrediente ing) 
	{
			eliminados.add(ing);
	}
		

		
		

	
	@Override
	public String getNombre() 
	/// Retorna el nombre del producto junto con sus adiciones y eliminados
	{
		String nombreAjustado = base.getNombre();
		/// Si hay adiciones, se agregan
		if (adicionados.size() != 0) 
		{
			nombreAjustado += "Con adición de: ";
			for (Ingrediente agregado : adicionados)
			{
				nombreAjustado +=  agregado.getNombre() + ", ";
			}
		}
		/// Si hay eliminados, se agregan
		if (eliminados.size() != 0)
		{
			nombreAjustado += "Sin: ";
			for (Ingrediente eliminado : eliminados)
			{
				nombreAjustado += eliminado.getNombre() + ", ";
			}
		}
		return nombreAjustado;
	}

	
	@Override
	public String generarTextoFactura() 
	{
		/// Devuelve el nombre del producto base y su precio individual
		String parteInicial = String.format("%-20s %10s", base.getNombre(), base.getPrecio()) + "\n";
		/// Devuelve el precio de las adiciones
		String textoAdiciones = "";
		for (Ingrediente adicion : adicionados)
		{
			textoAdiciones += String.format("%-20s %10s", "+" + adicion.getNombre(), adicion.getCostoAdicional()) + "\n";
		}
		String textoEliminaciones = "";
		for (Ingrediente eliminado : eliminados)
		{
			textoEliminaciones += "-" + eliminado.getNombre() + "\n";
		}
		/// Invoca el método getPrecio() para obtener el precio y meterlo en la factura
		String parteFinal = String.format("%-20s %10s", "AJUSTADO", getPrecio()) + "\n";
		String texto =  parteInicial + textoAdiciones + textoEliminaciones + parteFinal;
		return texto;
	}
}
