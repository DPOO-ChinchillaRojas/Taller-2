package procesamiento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
import modelo.Restaurante;


public class CalculadoraPedidos 
{	
	private ProductoAjustado producto_a;
	

	


	private Restaurante restaurante;
	
	
	public CalculadoraPedidos() throws FileNotFoundException, IOException
	{

		restaurante = new Restaurante();
		File i = new File("data/ingredientes.txt");
		File c = new File("data/combos.txt");
		File m = new File("data/menu.txt");
		File b = new File("data/bebidas.txt");
		
		restaurante.cargarInformacionRestaurante(i, m, c, b);
	}
	
	public Map<String, ProductoMenu> getMenu() throws FileNotFoundException, IOException
	{
		return restaurante.getMenuBase();
	}
	public Map<String, Combo> getCombos() throws FileNotFoundException, IOException
	{
		return restaurante.getCombos();
	}
	public Map<String, Ingrediente> getIngredientes() throws FileNotFoundException, IOException
	{
		return restaurante.getIngredientes();
	}
	public void anadirProducto(Pedido pedido, Producto producto) 
	{
		pedido.agregarProducto(producto);
	}
	public void anadirCombo(Pedido pedido, Combo combo) 
	{
		pedido.agregarProducto(combo);
	}
	public ProductoMenu getProducto(String nombre) 
	{
		return restaurante.getMenuBase().get(nombre);
	}
	public Producto ajustarProducto(Producto producto) 
	{
		String n = producto.getNombre();
		System.out.println("n");
		ProductoMenu pm = restaurante.getMenuBase().get(n);
		ProductoAjustado pa = new ProductoAjustado(pm);
		return pa;
	}
	public void adicionar(String nombre, String ing) 
	{
		producto_a = (ProductoAjustado) this.ajustarProducto(restaurante.getMenuBase().get(nombre));
		Ingrediente i = restaurante.getIngredientes().get(ing);
		producto_a.adicionar(i);
	}
	public void eliminar(String nombre, String ing) 
	{
		producto_a = (ProductoAjustado) this.ajustarProducto(restaurante.getMenuBase().get(nombre));
		Ingrediente i = restaurante.getIngredientes().get(ing);
		producto_a.eliminar(i);
	}
	public Combo getCombo(String nombre) 
	{
		return restaurante.getCombos().get(nombre);
	}
}
