package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Pedido 
{
	/************************************************
	 * ATRIBUTOS ** Cambiado - agregado atributo productos**
	 ************************************************/
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private List<Producto> productos;
	private List<Combo> combos;
	
	
	/************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public Pedido(String nombreCliente, String direccionCliente)
	{
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.productos = new ArrayList<Producto>();
		this.combos = new ArrayList<Combo>();
	}
	
	
	/************************************************
	 * MÉTODOS
	 ************************************************/
	public int getIdPedido()
	/// Retorna el Id del pedido en cuestión
	{
		return idPedido;
	}
	
	
	public void agregarProducto(Producto nuevoItem)
	/// Agrega un nuevo producto al Pedido
	{
		productos.add(nuevoItem);
	}
	
	public void agregarCombo(Combo nuevoItem)
	/// Agrega un nuevo producto al Pedido
	{
		combos.add(nuevoItem);
	}
	
	public List<Producto> getProductos()
	{
		return productos;
	}
	public List<Combo> getCombos()
	{
		return combos;
	}
	
	
	private int getPrecioNetoPedido()
	/// Calcula la suma de los precios de todos los productos en un pedido
	{
		int precioNeto = 0;
		for (Producto producto : productos)
		{
			precioNeto += producto.getPrecio();
		}
		return precioNeto;
	}
	
	
	private int getPrecioTotalPedido()
	/// Calcula el impuesto al IVA sobre el precio neto del pedido
	{
		int precioNeto = getPrecioNetoPedido();
		int precioIVA = getPrecioIVAPedido();
		int precioTotal = precioNeto + precioIVA;
		return precioTotal;
	}
	

	private int getPrecioIVAPedido()
	/// Calcula el impuesto al IVA sobre el precio neto del pedido
	{
		int precioNeto = getPrecioNetoPedido();
		double precioIVA = 0.19 * precioNeto;
		return (int) precioIVA;
	}
	
	public int contarCaloria ()
	{
		List<Producto> p = this.getProductos();
		int caloria = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("combo corral", 1060);
		map.put("combo corral queso", 1160);
		map.put("combo todo terreno", 1460);
		map.put("combo especial", 1460);
		
		map.put("agua con gas", 0);
		map.put("agua sin gas", 0);
		map.put("gaseosa", 260);
		
		map.put("lechuga", 5);
		map.put("tomate", 6);
		map.put("cebolla", 12);
		map.put("queso mozzarella", 120);
		map.put("huevo", 47);
		map.put("queso americano", 130);
		map.put("tocineta express", 162);
		map.put("papacallejera", 500);
		map.put("pepinillos", 4);
		map.put("cebolla grille", 15);
		map.put("suero costeño", 70);
		map.put("frijol refrito", 104);
		map.put("queso fundido", 180);
		map.put("tocineta picada", 162);
		map.put("piña", 15);
		for (HashMap.Entry<String, Integer> entry : map.entrySet())
		{
			for (int i=0;i<p.size();i++) {
				if (entry.getKey().equals(p.get(i).getNombre())) {
					caloria = caloria + entry.getValue(); 
				}
					
			}
		}
		return caloria;
}
	private String generarTextoFactura()
	/// Retorna el texto de la factura que muestra los datos del cliente y el total
	{
		String encabezado = "===========================================\n CLIENTE: " + nombreCliente
							+ "\n DIRECCIÓN DEL CLIENTE: " + direccionCliente +
							"===========================================\n";
		String elementos = "";
		for (Producto producto : productos)
		{
			elementos += producto.generarTextoFactura();
		}
		String subtotal = String.format("%-20s %10s", "SUBTOTAL NETO", getPrecioNetoPedido()) + "\n";
		String iva = String.format("%-20s %10s", "IVA (19%)", String.valueOf(getPrecioIVAPedido())) + "\n";
		String total = String.format("%-20s %10s", "TOTAL A PAGAR", getPrecioTotalPedido());
		String calorias = String.format("%-20s %10s", "CALORIAS: ", contarCaloria());
		/// Retorna los renglones formateados del recibo de un pedido
		return encabezado + elementos + calorias+ subtotal + iva + total ;
	}
	
	
	
	public void guardarFactura(File archivo) throws IOException
	/// Genera un texto de factura y lo guarda en un archivo nombrado como el id
	/// Adicionalmente incrementa el contador de pedidos en el atributo de clase
	{
		String textoFactura = generarTextoFactura();
		FileWriter writer = new FileWriter(archivo); 
		writer.write(textoFactura); /// Escribe el texto en el archivo dado
		writer.flush();
		writer.close(); /// Cierra el archivo en el cual escribió
		
		numeroPedidos += 1; /// Agrega un pedido a la cantidad de pedidos
	}
}



