package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
import modelo.Restaurante;
import procesamiento.CalculadoraPedidos;


public class Aplicacion 

{	
	private CalculadoraPedidos calculadora;
	
	private Restaurante restaurante;
	
	public Aplicacion() throws FileNotFoundException, IOException 
	{	
		calculadora = new CalculadoraPedidos();
		restaurante = new Restaurante();
		File i = new File("data/ingredientes.txt");
		File c = new File("data/combos.txt");
		File m = new File("data/menu.txt");
		File b = new File("data/bebidas.txt");
		
		restaurante.cargarInformacionRestaurante(i, m, c, b);
	}
	
	

	public void mostrarMenu() 
	{
		System.out.println("Bienvenido");
		System.out.println("1- Consultar el Menu");
		System.out.println("2- Realizar un pedido");
		System.out.println("0- Salir");
	}
		
	public void menuOpcion2() 
	{
		System.out.println("1- Añadir un producto al carrito");
		System.out.println("2- Añadir un combo al carrito");
		System.out.println("3- Finalizar el pedido");
		System.out.println("4- Cancelar el pedido");
	}
	public void menuProductosAjustados() 
	{
		System.out.println("Quiere agregar o retirar ingredientes a su pedido?");
		System.out.println("1- Si");
		System.out.println("2- No");
		
	}
	
	
	public void mostrarProductos() throws FileNotFoundException, IOException 
	{
		Map <String, ProductoMenu>menu = restaurante.getMenuBase();
		int i = 1;

		for (Map.Entry<String, ProductoMenu> entry: menu.entrySet()) 
		{
			
			System.out.println(i + "Producto: " + entry.getKey() + ": Costo: " + entry.getValue().getPrecio());
			i++;
		}
	}

	public void mostrarCombos() throws FileNotFoundException, IOException 
	{
		Map <String, Combo>combos = restaurante.getCombos();
		int i = 1;
		
		for (Map.Entry<String, Combo> entry: combos.entrySet()) 
		{
			
			System.out.println(i + "Costo: " + entry.getKey() + ": Costo: " + entry.getValue().getPrecio());
			i++;
		}
	}
	public void mostrarIngredientes() throws FileNotFoundException, IOException 
	{
		Map <String, Ingrediente>ingredientes = restaurante.getIngredientes();
		int i = 1;
		
		for (Map.Entry<String, Ingrediente> entry: ingredientes.entrySet()) 
		{
			
			System.out.println(i + "Ingrediente: " + entry.getKey() + ": Costo: " + entry.getValue().getCostoAdicional());
			i++;
		}
	}
	
	public String input(String mensaje)
	/// Método input para evitar escribir tantas veces lo mismo y usarlo análogo a Python
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public void ejecutarAplicación() throws FileNotFoundException, IOException 
	{
		boolean continuar = true;
		
		try 
		{
			while (continuar)
			{
				this.mostrarMenu(); 
				
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
				{
					this.mostrarProductos();
				}
				if (opcion_seleccionada == 2)
				{
					String nombre = input("Escriba su nombre");
					String direccion = input("Escriba su direccion");
					restaurante.iniciarPedido(nombre, direccion);
					Pedido pedido = restaurante.getPedidoEnCurso();
					boolean continuar2 = true;
					while (continuar2)
					{
						this.menuOpcion2();
						int opcion_seleccionada2 = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcion_seleccionada2 == 1) 
						{
							this.mostrarProductos();
							String seleccion = input("Seleccione el producto");
							this.menuProductosAjustados();
							ProductoMenu producto_seleccionado = calculadora.getProducto(seleccion);
							boolean continuar3 = true;
							while (continuar3) 
							{
								int opcion_seleccionada3 = Integer.parseInt(input("Por favor seleccione una opción"));
								if (opcion_seleccionada3 == 1) 
								{
									ProductoAjustado producto_a = new ProductoAjustado(producto_seleccionado);
									String respuesta = input("Quisiera añadir o retirar el ingrediente");
									
									if (respuesta.compareToIgnoreCase("añadir") == 0)
									{
										this.mostrarIngredientes();
										String ing_seleccionado = input("Que ingrediente quisiera añadir ");
										calculadora.adicionar(producto_seleccionado.getNombre(), ing_seleccionado);
										continuar3 = false;
									}
									else
									{
										this.mostrarIngredientes();
										String ing_seleccionado = input("Que ingrediente quisiera retirar");
										calculadora.eliminar(producto_seleccionado.getNombre(), ing_seleccionado);
										continuar3 = false;
										
									}
									calculadora.anadirProducto(pedido, producto_a);
									
								}
								else 
								{
									calculadora.anadirProducto(pedido, producto_seleccionado);
									continuar3=false;
								}
							}
							System.out.println(pedido);
						}
						if (opcion_seleccionada2 == 2) 
						{
							this.mostrarCombos();
							String seleccion = input("Seleccione el producto");
							Combo combo_seleccionado = calculadora.getCombo(seleccion);
							calculadora.anadirCombo(pedido, combo_seleccionado);
							System.out.println(pedido);
						}
						if (opcion_seleccionada2 == 3) 
						{
							for (Producto producto: pedido.getProductos()) 
							{
								System.out.println(producto.getNombre());
							}
							for (Combo combo: pedido.getCombos()) 
							{
								System.out.println(combo.getNombre());
							}
							System.out.println("Productos adicionados");
							restaurante.cerrarYGuardarPedido();
						}
						else if (opcion_seleccionada == 4) 
						{
							continuar2 = false;
						}
					}
				}
				if (opcion_seleccionada == 0)
				{
					continuar = false;
				}
			}
		}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.ejecutarAplicación();
	}

}
