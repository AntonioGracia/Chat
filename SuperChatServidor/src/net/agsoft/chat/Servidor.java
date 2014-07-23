package net.agsoft.chat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Servidor {

	public static final int TIMEOUT = 5000;
	
	private int puerto;
	public ServerSocket socket;
	public static Connection conexion;
	private ArrayList<Cliente> clientes;
	private ArrayList<String> nicks;
	public ArrayList<String> ips;
	private Database database;	

	
	public Servidor(int puerto) {
		this.puerto = puerto; 
		clientes = new ArrayList<Cliente>();
		nicks = new ArrayList<String>();
		ips = new ArrayList<String>();
	}
	
	public void anadirCliente(Cliente cliente) {
		cliente.setIp(cliente.socket.getInetAddress().getHostAddress());
		ips.add(cliente.socket.getInetAddress().getHostAddress());
		clientes.add(cliente);
	}
	
	public void eliminarCliente(Cliente cliente) {
		clientes.remove(cliente);
	}
	
	public void anadirNick(String nick) {
		nicks.add(nick);
	}
	
	public void eliminarNick(String nick) {
		nicks.remove(nick);
	}
	
	
	public void enviarATodos(String mensaje) {
		
		for (Cliente cliente : clientes) {
			cliente.getSalida().println(mensaje);
		}
	}
	
	public void enviarMensajePrivado(String mensaje, String nickSusurro, Cliente cliente){
		
		for(Cliente cli: clientes){
			if(cli.getNick().equals(nickSusurro)){
						
				cli.getSalida().println(mensaje);
				cliente.getSalida().println(mensaje);
		
				
				break;
				
			}
		}
	}
	
	
	public void registrar(String nombre, String contrasena, Cliente cliente){
		
		Usuario nuevoUsuario = new Usuario();
		String nombrePc = null;
		try {
			nombrePc = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ip = cliente.socket.getInetAddress().getHostAddress();
		nuevoUsuario.setNombrepc(nombrePc);
		nuevoUsuario.setIp(ip);
		nuevoUsuario.setNombreUsuario(nombre);
		nuevoUsuario.setContrasena(contrasena);

		conectarBase();
		try {
			database.nuevo(nuevoUsuario);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private void conectarBase() {
		
		Properties configuracion = new Properties();
		
		try {
			configuracion.load(new FileInputStream("configuracion.props"));
			
			String driver = configuracion.getProperty("driver");
			String protocolo = configuracion.getProperty("protocolo");
			String servidor = configuracion.getProperty("servidor");
			String puerto = configuracion.getProperty("puerto");
			String baseDatos = configuracion.getProperty("basedatos");
			String usuario = configuracion.getProperty("usuario");
			String contrasena = configuracion.getProperty("contrasena");
		
			Class.forName(driver).newInstance();
			conexion = DriverManager.getConnection(
					protocolo + 
					servidor + ":" + puerto +
					"/" + baseDatos, 
					usuario, contrasena);
			database = new Database(conexion);
			
		
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "Error al leer el fichero de configuración");
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Error al leer el fichero de configuración");
		} catch (ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar el driver de la Base de Datos");
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la Base de Datos");
			sqle.printStackTrace();
		} catch (InstantiationException ie) {
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
	}
	
	public void enviarNicks() {
		
		
		
		for (Cliente cliente : clientes) {
			cliente.getSalida().println(obtenerNicks());
			
		}
	}
	

	public String obtenerNicks() {
	
		String nicks = "/nicks,";
		
		for (Cliente cliente : clientes) {
			if (cliente.getNick() != null)
				nicks += cliente.getNick() + ",";
		}
		
		return nicks;
	}
	
	public int getNumeroClientes() {
		return clientes.size();
	}
	

	public boolean estaConectado() {
		return !socket.isClosed();
	}
	
	/**
	 * Inicia el servidor
	 */
	public void conectar() throws IOException {
		socket = new ServerSocket(puerto);
	}
	

	public void desconectar() throws IOException {
		socket.close();
		
	}

	public Socket escuchar() throws IOException {
		return socket.accept();
	}
	
	public boolean existeIp(String ipUser){
		
		for(String ip: ips){
			System.out.println("Holas "+ips);
			
			if(ip.equals(ipUser)){
				return true;
			}
			
		}
		return false;
	}
	
	public boolean existeNick(String nickUser){
		
		for(String nick: nicks){
			if(nicks.contains(nick)){
				return true;
			}
		}
		return false;
	}
}
