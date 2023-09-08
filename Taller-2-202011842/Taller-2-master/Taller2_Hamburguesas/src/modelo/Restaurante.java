package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Restaurante 
{
	/************************************************
	 * ATRIBUTOS ** Cambiado - agregado atributos ingredientes, menu, combos **
	 ************************************************/
	private Pedido pedidoEnCurso;
	private Map<String, ProductoMenu> menu;
	private Map<String, ProductoMenu> bebidas;
	private Map<String, Ingrediente> ingredientes ;
	private Map<String, Combo> combos;
	
	
	/************************************************
	 * CONSTRUCTOR
	 ************************************************/
	public Restaurante()
	/// Construye una clase Restaurante con 3 tablas de Hash para los archivos de los productos
	{
		this.ingredientes = new HashMap<>();
		this.menu = new HashMap<>();
		this.combos = new HashMap<>();
		this.pedidoEnCurso = null;
	}
	
	
	/************************************************
	 * MÉTODOS
	 ************************************************/
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	/// Crea un nuevo pedido invocando la clase Pedido y pasando el nombre y la dirección del cliente
	{
		pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
	}
	
	
	public void cerrarYGuardarPedido() throws IOException
	/// Si hay un pedido en proceso, lo cierra y lo guarda con su id como nombre de archivo
	{
		if (! pedidoEnCurso.equals(null))
		{
			/// Se generan aleatoriamente los IDs entre 0 y 25 millones
			Random rand = new Random();
			int id = rand.nextInt(250000000);
			/// Se utiliza el id para generar el nombre de archivo
			String nombreArchivo = String.valueOf(id) + ".txt";
			/// Se crea el archivo para guardar el pedido
			File archivo = new File(nombreArchivo);
			/// Se guarda la factura en un archivo con el nombre creado
			pedidoEnCurso.guardarFactura(archivo);
			pedidoEnCurso = null;
		}	
	}
	
	
	public Pedido getPedidoEnCurso()
	/// Retorna el pedido en curso
	{
		return pedidoEnCurso;
	}
	
	
	public Map<String, ProductoMenu> getMenuBase()
	/// Retorna el menú, que es un arreglo con todos los productos
	{
		return menu;
	}
	public Map<String, ProductoMenu> getBebidas()
	/// Retorna el menú, que es un arreglo con todos los productos
	{
		return bebidas;
	}
	
	
	public Map<String, Ingrediente>  getIngredientes()
	/// Retorna los ingredientes dentro de un arreglo
	{
		return ingredientes;
	}
	
	public Map<String, Combo> getCombos()
	/// Retorna los ingredientes dentro de un arreglo
	{
		return combos;
	}
	
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws IOException
	/// Carga toda la información desde los archivos invocando a los otros métodos de carga
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarBebidas(archivoBebidas);
	}

	
	public Map<String, Ingrediente> cargarIngredientes(File archivoIngredientes) throws FileNotFoundException, IOException
	{
		// Creacion de estructuras de datos
		
		
		// creador del analizador linea por linea
		BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
		String linea = br.readLine(); 
		
		// se cargan en el mapa de la forma (nombre, Ingrediente)
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombreIngrediente = partes[0];
			int costoAdicional = Integer.parseInt(partes[1]);
			Ingrediente ingrediente = new Ingrediente(nombreIngrediente, costoAdicional);
			this.ingredientes.put(nombreIngrediente, ingrediente);
			linea = br.readLine();
		}
		br.close();
		
		return ingredientes;
		
	}
	
	
	public Map<String, ProductoMenu> cargarMenu(File archivoMenu) throws FileNotFoundException, IOException
	{
		// Creacion de estructuras de datos
		
		
		// creador del analizador linea por linea
		BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
		String linea = br.readLine(); 
		
		// Los archivos vienen de la forma (nombre, precio) de esta forma se asigna en el mapa (nombre, Producto)
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombreProducto = partes[0];
			int costoBase = Integer.parseInt(partes[1]);
			ProductoMenu producto = new ProductoMenu(nombreProducto, costoBase);
			this.menu.put(nombreProducto, producto);
			linea = br.readLine();
		}
		br.close();
		return menu;
	}
	
	public Map<String, ProductoMenu> cargarBebidas(File archivoBebidas) throws FileNotFoundException, IOException
	{
		// Creacion de estructuras de datos
		
		
		// creador del analizador linea por linea
		BufferedReader br = new BufferedReader(new FileReader(archivoBebidas));
		String linea = br.readLine(); 
		
		// Los archivos vienen de la forma (nombre, precio) de esta forma se asigna en el mapa (nombre, Producto)
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombreProducto = partes[0];
			int costoBase = Integer.parseInt(partes[1]);
			ProductoMenu producto = new ProductoMenu(nombreProducto, costoBase);
			this.menu.put(nombreProducto, producto);
			linea = br.readLine();
		}
		br.close();
		return menu;
	}
	

	
	
	public Map<String, Combo> cargarCombos(File archivoCombos) throws FileNotFoundException, IOException
	{
		//Creacion del lector de el archivo txt
		BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
		String linea = br.readLine(); 
		
		//Se crea un combo utilizando los productos de la carga de productos realizada anteriormente
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombreCombo = partes[0];
			double descuento = Double.parseDouble(partes[1].substring(0, 1));
			Combo combo = new Combo(nombreCombo, descuento);
			
			for (int i = 2; i<partes.length; i++) 
			{
				ProductoMenu producto = menu.get(partes[i]);
				combo.agregarItemACombo(producto);
			}
			
			combos.put(nombreCombo, combo);
			linea = br.readLine();
		}
		br.close();
		
		return combos;
		
	}
	
}



