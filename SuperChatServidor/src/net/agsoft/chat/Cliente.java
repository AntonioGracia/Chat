package net.agsoft.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente extends Thread {

	public Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;
	private Servidor servidor;
	private String nick;
	private boolean contestaPing;
	private boolean conectado;
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Cliente(Socket socket, Servidor servidor) throws IOException {
		this.socket = socket;
		this.servidor = servidor;
		
		salida = new PrintWriter(socket.getOutputStream(), true);
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public PrintWriter getSalida() {
		return salida;
	}
	
	public void cambiarNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	private void desconectar() throws IOException {
		conectado = false;
		socket.close();
		servidor.eliminarCliente(this);
		servidor.enviarNicks();
	}
	
	@Override
	public void run() {
		System.out.println("Iniciando comunicación con el cliente");
		
		salida.println("/server Hola " + socket.getInetAddress().getHostAddress());
		salida.println("/server Escribe tu nick y pulsa enter");
		try {
			String nick = entrada.readLine();
			if(servidor.existeNick(nick)==true)
				salida.println("/server El nick " + nick + " ya existe");
			else{
				cambiarNick(nick);
				salida.println("/server Bienvenido " + nick);
				salida.println("/server Hay " + servidor.getNumeroClientes() + " usuarios conectados");
				salida.println("/server Cuando escribas '/quit', se terminará la conexión");
				
				servidor.anadirCliente(this);
				servidor.enviarNicks();
				conectado = true;
			}
			
			Thread hiloPing = new Thread(new Runnable() {
				@Override
				public void run() {
					while (conectado) {
						
						contestaPing = false;
						salida.println("/ping");
						System.out.println("ping");
						
						try {
							Thread.sleep(Servidor.TIMEOUT);
						} catch (InterruptedException ie) {}
						
						if (!contestaPing)
							try {
								desconectar();
							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
					}
				}
			});
			hiloPing.start();
		
			String linea = null;
		

			while (conectado) {
				
				linea = entrada.readLine();
				
				if (linea.equals("/quit")) {
					salida.println("/server Saliendo . . .");
					desconectar();
					break;
				}
				else if (linea.equals("/pong")) {
					System.out.println("pong");
					contestaPing = true;
					continue;
				}
				
				else if(linea.startsWith("/privado")){
					
					String[] x = linea.split(" ");
					String user = x[1];
					String mensaje = ":" + this.nick + " : ";
					for(int i=2; i<x.length; i++)
					
						mensaje += x[i] + " ";
					
					servidor.enviarMensajePrivado("/privado " + mensaje, user, this);
					continue;
				}
				else if(linea.startsWith("/registrar")){
					String[] x = linea.split(" ");
					String usuario = x[1];
					String contrasena = x[2];
									
					servidor.registrar("/registrar " +  usuario, contrasena, this);
					continue;
				}
				
				
				servidor.enviarATodos("/users " + nick + " " + linea);
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
